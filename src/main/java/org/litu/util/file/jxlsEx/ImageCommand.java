package org.litu.util.file.jxlsEx;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.*;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.litu.util.file.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>插入图片</p>
 * jx:image(
 * src="byte[] | 图片路径（绝对绝对路径 / 相对路径，需要配置ImageRoot）",
 * lastCell="图片右下角单元格坐标，左上角坐为指令所在单元格"
 * [,imageType="JPG"]
 * [,size="auto | original"]
 * [,scaleX="1"]
 * [,scaleY="1"]
 * )
 *
 * @Author lnk
 * @Date 2018/1/23
 */
public class ImageCommand extends AbstractCommand {
    private byte[] imageBytes;
    private ImageType imageType = ImageType.PNG;
    private Area area;

    /**
     * 图片源，可以byte[]、JxlsImage对象和图片路径
     */
    private String src;
    /**
     * 插入图片大小
     *
     * @see <a href="http://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Picture.html">Picture.resize(scaleX,scaleY)</a>
     */
    private String scaleX;
    private String scaleY;
    /**
     * 自适应大小类型<br>
     * auto 默认，自适应单元格大小<br>
     * original 图片原大小
     */
    private String size;

    public ImageCommand() {
    }

    public ImageCommand(String image, ImageType imageType) {
        this.src = image;
        this.imageType = imageType;
    }

    public ImageCommand(byte[] imageBytes, ImageType imageType) {
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    @Override
    public Command addArea(Area area) {
        if (super.getAreaList().size() >= 1) {
            throw new IllegalArgumentException("You can add only a single area to 'image' command");
        }
        this.area = area;
        return super.addArea(area);
    }

    @Override
    public String getName() {
        return "image";
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        if (area == null) {
            throw new IllegalArgumentException("No area is defined for image command");
        }
        Transformer transformer = getTransformer();
        Size size = area.getSize();

        try {
            JxlsImage img = getImage(context);
            if (img != null) {
                if (transformer instanceof PoiTransformer) {
                    addImage(cellRef, context, (PoiTransformer) transformer, img);
                } else {
                    //获取图片显示区域是时候，多加一行和一列，获取完之后再恢复原来大小
                    size.setWidth(size.getWidth() + 1);
                    size.setHeight(size.getHeight() + 1);
                    AreaRef areaRef = new AreaRef(cellRef, size);
                    size.setWidth(size.getWidth() - 1);
                    size.setHeight(size.getHeight() - 1);
                    transformer.addImage(areaRef, img.getPictureData(), img.getJxlsImageType());
                }
            }
        } catch (Exception e) {
            Boolean ignoreImageMiss = (Boolean) context.getVar("_ignoreImageMiss");
            //是否忽略图片读取失败，并继续生成excel后面操作，否则终止生成
            if (ignoreImageMiss == null || !ignoreImageMiss) {
                throw new IllegalArgumentException("出现异常，终止生成excel", e);
            }
        }
        //恢复原有的样式
        area.applyAt(cellRef, context);
        return size;
    }

    private void addImage(CellRef cellRef, Context context, PoiTransformer transformer, JxlsImage img) {
        Workbook wb = transformer.getWorkbook();
        int pictureIdx = wb.addPicture(img.getPictureData(), img.getWorkbookImageType());
        Sheet sheet = wb.getSheet(cellRef.getSheetName());
        Drawing drawing = sheet.createDrawingPatriarch();
        CreationHelper helper = wb.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(cellRef.getCol());
        anchor.setCol2(cellRef.getCol() + area.getSize().getWidth());
        anchor.setRow1(cellRef.getRow());
        anchor.setRow2(cellRef.getRow() + area.getSize().getHeight());
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        if (StringUtils.isNotBlank(scaleX) || StringUtils.isNotBlank(scaleY)) {
            double scale_x = 1d, scale_y = 1d;
            if (StringUtils.isNotBlank(scaleX)) {
                Object scaleXObj = getTransformationConfig().getExpressionEvaluator().evaluate(scaleX, context.toMap());
                scale_x = Double.valueOf(scaleXObj.toString());
            }
            if (StringUtils.isNotBlank(scaleY)) {
                Object scaleXObj = getTransformationConfig().getExpressionEvaluator().evaluate(scaleY, context.toMap());
                scale_y = Double.valueOf(scaleXObj.toString());
            }

            pict.resize(scale_x, scale_y);
        } else if (JxlsImage.IMAGE_SIZE_TYPE_ORIGINAL.equalsIgnoreCase(size)) {
            pict.resize();
        } else {
            pict.resize(1d);
        }
    }

    private JxlsImage getImage(Context context) throws IOException {
        if (imageBytes == null && src != null) {
            Object imgObj = getTransformationConfig().getExpressionEvaluator().evaluate(src, context.toMap());
            if (imgObj != null) {
                if (imgObj instanceof byte[]) {
                    imageBytes = (byte[]) imgObj;
                } else if (imgObj instanceof JxlsImage) {
                    return (JxlsImage) imgObj;
                } else if (imgObj instanceof String) {
                    String imgSrc = (String) imgObj;

                    String imageRoot = (String) context.getVar("_imageRoot");
                    //判断是相对路径还是绝对路径
                    if (!FileUtil.isAbsolutePath(imgSrc)) {
                        //相对路径就从模板目录获取文件
                        return new JxlsImage(imageRoot + File.separator + imgSrc);
                    } else {
                        //绝对路径
                        return new JxlsImage(imgSrc);
                    }
                }
            }
        }

        if (imageBytes != null) {
            JxlsImage img = new JxlsImage();
            img.setPictureData(imageBytes);
            if (imageType != null) {
                img.setPictureType(imageType.toString());
            } else {
                img.setPictureType("jpg");
            }
            return img;
        }

        throw new IllegalArgumentException("图片读取失败 src=" + src);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setImageType(String strType) {
        imageType = ImageType.valueOf(strType);
    }

    public String getScaleX() {
        return scaleX;
    }

    public void setScaleX(String scaleX) {
        this.scaleX = scaleX;
    }

    public String getScaleY() {
        return scaleY;
    }

    public void setScaleY(String scaleY) {
        this.scaleY = scaleY;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

class JxlsImage {
    public JxlsImage() {

    }

    public JxlsImage(String imgPath) {
        try (InputStream ins = new FileInputStream(imgPath)) {
            byte[] imgData = IOUtils.toByteArray(ins);
            this.setPictureData(imgData);
        } catch (IOException e) {
            this.setPictureData(null);
        }

        String imageType = FileUtil.getFileSuffix(imgPath);
        this.setPictureType(imageType);
    }

    public JxlsImage(InputStream is, String name) throws IOException {
        this.setPictureData(IOUtils.toByteArray(is));

        String imageType = FileUtil.getFileSuffix(name);
        this.setPictureType(imageType);
    }

    public static final String IMAGE_SIZE_TYPE_AUTO = "auto";
    public static final String IMAGE_SIZE_TYPE_ORIGINAL = "original";
    private byte[] pictureData;
    private String pictureType;

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public int getWorkbookImageType() {
        switch (pictureType.toUpperCase()) {
            case "PNG":
                return Workbook.PICTURE_TYPE_PNG;
            case "EMF":
                return Workbook.PICTURE_TYPE_EMF;
            case "WMF":
                return Workbook.PICTURE_TYPE_WMF;
            case "PICT":
                return Workbook.PICTURE_TYPE_PICT;
            case "DIB":
                return Workbook.PICTURE_TYPE_DIB;
            default:
                return Workbook.PICTURE_TYPE_JPEG;
        }
    }

    public ImageType getJxlsImageType() {
        if ("jpg".equalsIgnoreCase(pictureType)) {
            return ImageType.JPEG;
        }
        return ImageType.valueOf(pictureType);
    }
}