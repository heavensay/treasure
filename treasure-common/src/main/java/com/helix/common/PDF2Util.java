package com.helix.common;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * pdf工具类，支持html转pdf，使用itextpdf，xmlworker
 * 大部分windows系统自带宋体，可直接中文中文；linux可以系统安装字体，或者直接{@link #createFront(String)}加载字体来支持
 */
public class PDF2Util {

    private static XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
    private static XMLWorkerFontProvider xmlWorkerFontProvider = new XMLWorkerFontProvider();

    /**
     * html转pdf
     * @param htmlPath html文件路径
     * @param pdfPath pdf路径
     */
    public static void html2Pdf(String htmlPath,String pdfPath){
        Document document = new Document(PageSize.LETTER);

        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            FileInputStream fis = new FileInputStream(new File(htmlPath));
            InputStream inCssFile = null;
            worker.parseXHtml(pdfWriter, document,fis, inCssFile,
                    Charset.forName("UTF-8"), xmlWorkerFontProvider);
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("html转pdf错误",e);
        }
    }

    /**
     * 添加字体
     * @param frontPath 字体路径
     */
    public static void createFront(String frontPath){
        xmlWorkerFontProvider.getFont(frontPath);
    }

    /**
     * 字体设置
     */
    public static class FrontConfig{
        /**
         * 添加字体
         * 添加一次既可
         * @param frontPath 字体路径
         */
        public static void createFront(String frontPath){
            xmlWorkerFontProvider.getFont(frontPath);
        }
    }
}
