package org.litu.util.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.litu.util.file.jxlsEx.ImageCommand;
import org.litu.util.file.jxlsEx.MergeCommand;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ExcelPoiUtil {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
    public static String getCellValueString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                if (DateUtil.isCellDateFormatted(cell)) {
//                    short format = cell.getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
//                    if (format == 20 || format == 32) {
//                        sdf = new SimpleDateFormat("HH:mm");
//                    } else if (format == 14 || format == 31 || format == 57 || format == 58) {
//                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
//                        sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        double value = cell.getNumericCellValue();
//                        Date date = DateUtil.getJavaDate(value);
//                        cellValue = sdf.format(date);
//                    } else {// 日期
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    }
                    try {
                        cellValue = sdf.format(cell.getDateCellValue());// 日期
                    } catch (Exception e) {
                        try {
                            throw new Exception("exception on get date data !".concat(e.toString()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Double doubleValue = cell.getNumericCellValue();
                    // 格式化科学计数法，取一位整数
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(doubleValue);
                }
                break;
            case STRING:    //字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                cellValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                cellValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return cellValue == null ? null : cellValue.trim();
    }


    /*------------------------ 通过Jxls进行文档的导出操作 -------------------------*/

//    private static final String JXLS_TEMPLATE_PATH = "/ExportTemplate";
//    private static final Resource excelTemplate = new ClassPathResource(JXLS_TEMPLATE_PATH, ExcelPoiUtil.class);
//    static {
//        try {
//            excelTemplate.getFile();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static final String JXLS_TEMPLATE_PATH = "exportTemplate/";


    /**
     * 返回模板的相对路径
     *
     * @param templateName 模板名称
     * @return
     */
    public static String getJxlsTempPath(String templateName) {
        return JXLS_TEMPLATE_PATH + templateName;
    }

    /**
     * 加一个带图片导出的方法
     *
     * @param templatePath 模板的绝对路径
     * @param ImageRoot    如果使用相对路径图片的话，图片的根路径
     * @param data         导出的数据
     * @param out          输出流
     * @throws Exception
     */
    public static void jxlsExport(String templatePath, String ImageRoot, Map<String, Object> data, OutputStream out) throws Exception {
        if (StringUtils.isBlank(templatePath) || data == null || out == null) {
            return;
        }

        XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("image", ImageCommand.class);

        try (InputStream in = new FileInputStream(new File(templatePath))) {
            Context context = new Context(data);
            context.putVar("_imageRoot", ImageRoot);    //图片导出的图片本地根路径
            context.putVar("_ignoreImageMiss", true);   // 如果图片不存在，则直接忽略
            JxlsHelper.getInstance().processTemplate(in, out, context);
        } catch (Exception e) {
            throw e;
        }
    }
}
