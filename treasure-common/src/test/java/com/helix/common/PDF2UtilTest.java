package com.helix.common;

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

/**
 * itextpdf组件测试
 * @author ljy
 * @date 2019/6/29 14:22
 */
public class PDF2UtilTest {

    @Test
    public void html2Pdf() throws Exception{
        String htmlAbsolutePath = this.getClass().getClassLoader().getResource("com/helix/common/simple.html").getPath();
        PDF2Util.html2Pdf(htmlAbsolutePath,"D:/cdcd.pdf");
        PDF2Util.html2Pdf(htmlAbsolutePath,"D:/cdcd2.pdf");
    }

    @Test
    public void html2PdfCustomFront() throws Exception{
        PDF2Util.FrontConfig.createFront("D:/造字工房韵黑体.ttf");
        String htmlAbsolutePath = this.getClass().getClassLoader().getResource("com/helix/common/simple.html").getPath();
        PDF2Util.html2Pdf(htmlAbsolutePath,"D:/cdcd.pdf");
        PDF2Util.html2Pdf(htmlAbsolutePath,"D:/cdcd2.pdf");
    }


    /**
     * 基本html转pdf
     * @throws Exception
     */
    @Test
    public void html2PdfBase() throws Exception{
        BaseFont bfChinese = BaseFont.createFont(
                "font/simsun.ttc,0", BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        Document document = new Document(PageSize.LETTER);

        PdfWriter.getInstance(document, new FileOutputStream("D:/tmp1.pdf"));
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/helix/common/simple.html");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bs = new byte[1024];
        int n = 0;
        if((n=is.read(bs,0,1024)) != -1){
            baos.write(bs,0,n);
        }
        htmlWorker.parse(new StringReader(baos.toString("utf-8")));
        document.close();
    }

    /**
     * 生成pdf文件
     * @throws Exception
     */
    @Test
    public void generatePdf() throws Exception{
        Document document = new Document();
        PdfWriter writer = null;
        writer = PdfWriter.getInstance(document, new FileOutputStream("D:/tmp.pdf"));
        document.open();

        // 使用iTextAsian.jar中的字体
        BaseFont baseFont = BaseFont.createFont("font/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        document.add(new Paragraph("this is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF document"));
        document.add(new Paragraph("this is b Hello World PDF document"));
        document.add(new Paragraph("这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落", font));// 注意这里要设置paragaph的字体

        document.close();
        writer.close();
    }

    @Test
    public void html2PdfSimple()throws Exception{
        Document document = new Document();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/tmp1.pdf"));
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/helix/common/ddd.html");
        document.open();

        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(pdfWriter, document,is);
        document.close();
    }

    /**
     * html转pdf，支持中文
     * 如果是window的话，默认都是带了simsum.ttc宋体了；linux需要自己安装
     * @throws Exception
     */
    @Test
    public void html2PdfCh()throws Exception{
        Document document = new Document(PageSize.LETTER);

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/tmp1.pdf"));
        document.open();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/helix/common/simple.html");

        InputStream is2 = null;
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(pdfWriter, document,is, is2,
                Charset.forName("UTF-8"), new AsianFontProvider());
//        worker.parseXHtml(pdfWriter, document,is, is2, Charset.forName("UTF-8"));//也能支持中文
        document.close();
    }
}

/**
 * 字体
 */
 class AsianFontProvider extends XMLWorkerFontProvider {

    public Font getFont(final String fontname, final String encoding,
                        final boolean embedded, final float size, final int style,
                        final BaseColor color) {
        BaseFont bf = null;
        try {
//            //自定义字体，后面",0“，跟字体组有关，e.g. 雅黑有三种，常规、加粗、极细
            bf = BaseFont.createFont(
                    "font/simsun.ttc,0", BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, size, style, color);
        font.setColor(color);
        return font;
    }
}