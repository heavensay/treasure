package com.helix.common.util;

import org.junit.Test;

import java.io.File;
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

        File file = new File(url.getFile());
        System.out.println(file.getAbsolutePath());
        PDFUtils.cvtHtmlToPdfByFile(file,"temp.pdf");
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
}