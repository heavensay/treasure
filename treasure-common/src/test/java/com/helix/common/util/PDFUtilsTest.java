package com.helix.common.util;

import com.lowagie.text.pdf.BaseFont;
import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author lijianyu
 * @date 2018/10/15 17:44
 */
public class PDFUtilsTest {

    @Test
    public void cvtHtmlfileToPdf() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        /**
         getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource("com/helix/common/util/simple.html");

        File file = new File("D:/work/融资租赁/租赁协议/ttt.html");
        System.out.println(file.getAbsolutePath());
        PDFUtils.cvtHtmlToPdfByFile(file,"D:\\work\\融资租赁\\租赁协议\\1.pdf");
    }

    @Test
    public void cvtHtmlToPdfByURL() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        /**
         getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource("com/helix/common/util/simple.html");

        File file = new File(url.getFile());
        System.out.println(file.getAbsolutePath());
        PDFUtils.cvtHtmlToPdfByURL(url,"temp.pdf");
    }

    @Test
    public void cvtHtmltextToPdf() {
    }

    @Test
    public void cvthtmlToPdf() throws Exception{
        //lease-agreement-gangqin.htm
        String url = "D:/work/融资租赁/品类/fur/协议/ttt.html";
        String targetPath = "D:/work/融资租赁/品类/fur/协议/1.pdf";
        String fontPath = "attach/font/simsun.ttc";
        File targetFile = new File(targetPath);
        if (targetFile.exists())

        {
            targetFile.delete();
        }
        try (OutputStream os = new FileOutputStream(targetFile))
        {
//			PDFEncryption pdfEncryption = new PDFEncryption(null, null, PdfWriter.ALLOW_PRINTING);
            ITextRenderer renderer = new ITextRenderer();
//			renderer.setPDFEncryption(pdfEncryption);
            renderer.setDocument(new File(url));
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        }
    }
}