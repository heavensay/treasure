package com.helix.common.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * freemarker工具类
 */
public class FreemarkerParse {

    /**
     * 根据传入的模板内容来处理
     * @param templateContent
     * @param data
     * @param out
     * @throws TemplateException
     * @throws IOException
     */
    public static void processContent(String templateContent, Object data, Writer out) throws TemplateException, IOException {
        Object dataModel = data;
        Template t = new Template(null, templateContent, null);
        t.process(dataModel, out);
        out.flush();
    }

    /**
     * 根据传入的模板内容来处理,并返回
     * @param templateContent
     * @param data
     * @return 处理好之后的模板内容
     * @throws TemplateException
     * @throws IOException
     */
    public static String getProcessedContent(String templateContent, Object data) throws TemplateException, IOException {
        Object dataModel = data != null ? data : new Object();

        StringWriter sw = new StringWriter();
        Template t = new Template(null, templateContent, null);
        t.process(dataModel, sw);
        sw.close();
        return sw.toString();
    }
}
