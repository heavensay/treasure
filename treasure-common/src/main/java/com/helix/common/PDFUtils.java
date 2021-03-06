package com.helix.common;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * @deprecated 很久没人维护了，而且对中文换行需要改源码，不建议使用 instead by {@link PDF2Util}
 * core-renderer.R8不支持中文换行;
 * 还需要中文字体simsun.ttc支持
 * 想要支持中文换行，需要使用特质的core-renderer.jar包；或者其他方式转换html -> pdf，如wkhtmltopdf(exe)、xpdf(exe)、itextpdf(jar)
 * pdf转换工具类
 */
public class PDFUtils {

    private static String DEFAULT_FONT_PATH = "font/simsun.ttc";

    private static String fontPath = DEFAULT_FONT_PATH;

    /**
     * 转换html文件为pdf，并存放到<code>targetPath<code/>下面
     * @param htmlFile html文件
     * @param targetPath pdf存放的路径
     * @throws Exception
     */
    public static void cvtHtmlToPdfByFile(File htmlFile, String targetPath) throws Exception {
        String url = htmlFile.toURI().toURL().toString();
        File targetFile = new File(targetPath);
        if (targetFile.exists()){
            targetFile.delete();
        }

        try (OutputStream os = new FileOutputStream(targetFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            //font_path获取不到字体文件，可以试试把下面注释去掉，执行
            //fontResolver.addFont("/com/helix/common/util/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont(DEFAULT_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
        }
    }

    /**
     * 转换html文件为pdf，并存放到<code>targetPath<code/>下面
     * @param htmlURL html文件URL
     * @param targetPath pdf存放的路径
     * @throws Exception
     */
    public static void cvtHtmlToPdfByURL(URL htmlURL, String targetPath) throws Exception {
        String url = htmlURL.toURI().toURL().toString();
        File targetFile = new File(targetPath);
        if (targetFile.exists())

        {
            targetFile.delete();
        }
        try (OutputStream os = new FileOutputStream(targetFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            //font_path获取不到字体文件，可以试试把下面注释去掉，执行
            //fontResolver.addFont("/com/helix/common/util/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont(DEFAULT_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        }
    }

    /**
     * html转pdf，note：os关闭由传入方控制
     * 传输内容
     * @param htmlText html内容
     * @param os pdf输出流
     * @throws Exception
     */
    public static void cvtHtmltextToPdf(String htmlText, OutputStream os) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlText);
        // 解决中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(DEFAULT_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(os);
        renderer.finishPDF();
        os.flush();
    }


    /**
     * util中改变属性值，这种设计模式有问题；
     * @param fnotPath
     */
    public static void setFnotPath(String fnotPath){
        PDFUtils.fontPath = fnotPath;
    }
}
