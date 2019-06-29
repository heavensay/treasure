package com.helix.common;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;

/**
 * @author ljy
 * @date 2019/6/29 14:22
 */
public class PDFUtil2Test {

    @Test
    public void html2Pdf() throws Exception{
        BaseFont bfChinese = BaseFont.createFont(
                "font/simsun.ttc", BaseFont.IDENTITY_H,
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

    @Test
    public void convert() throws Exception{
        Document document = new Document();
        PdfWriter writer = null;
        writer = PdfWriter.getInstance(document, new FileOutputStream("D:/tmp2.pdf"));
        document.open();

        // 使用iTextAsian.jar中的字体
        BaseFont baseFont = BaseFont.createFont("font/simsun.ttc,1", BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        document.add(new Paragraph("this is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF documentthis is A Hello World PDF document"));
        document.add(new Paragraph("this is b Hello World PDF document"));
        document.add(new Paragraph("这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落这里是中文段落", font));// 注意这里要设置paragaph的字体

        document.close();
        writer.close();
    }
}
