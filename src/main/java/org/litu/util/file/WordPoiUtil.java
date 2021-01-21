package org.litu.util.file;

import com.deepoove.poi.XWPFTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;


/**
 * word操作工具类
 */
public class WordPoiUtil {

    static Logger logger = LoggerFactory.getLogger(WordPoiUtil.class);

    /**
     * 加一个带图片导出的方法
     *
     * @param templatePath 模板的绝对路径
     * @param data         导出的数据
     * @param out          输出流
     * @throws Exception
     */
    public static void wordExport(String templatePath, Map<String, Object> data, OutputStream out) throws IOException {
        if (StringUtils.isBlank(templatePath) || data == null || out == null) {
            return;
        }

        try (InputStream in = new FileInputStream(new File(templatePath))) {
            XWPFTemplate template = XWPFTemplate.compile(in).render(data);
            template.write(out);
            template.close();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 本地路径word编辑并保存
     *
     * @param filePathLocal
     * @param mapData
     */
    public static void editDoc(String filePathLocal, Map<String, Object> mapData) {
        XWPFTemplate template = XWPFTemplate.compile(filePathLocal).render(mapData);
        FileOutputStream out;
        try {
            out = new FileOutputStream(filePathLocal);
            template.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并两个文档到新的文件
     *
     * @param outfile 输出文件
     * @param file1   要合并的文件1
     * @param file2   要合并的文件2
     * @throws Exception
     */
    public static void appendDoc(String outfile, String file1, String file2) throws Exception {
        InputStream in1 = null;
        InputStream in2 = null;

        OPCPackage src1Package = null;
        OPCPackage src2Package = null;

        try {
            in1 = new FileInputStream(file1);
            in2 = new FileInputStream(file2);
            src1Package = OPCPackage.open(in1);
            src2Package = OPCPackage.open(in2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFDocument src1Document = new XWPFDocument(src1Package);
        CTBody src1Body = src1Document.getDocument().getBody();
        XWPFParagraph p = src1Document.createParagraph();
        //设置分页符
        //   p.setPageBreak(true);
        XWPFDocument src2Document = new XWPFDocument(src2Package);
        CTBody src2Body = src2Document.getDocument().getBody();
        appendBody(src1Body, src2Body);
        OutputStream dest = new FileOutputStream(outfile);
        src1Document.write(dest);
        src1Document.close();
        src2Document.close();
        in1.close();
        in2.close();
        dest.close();
    }

    private static void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
        src.set(makeBody);
    }
}
