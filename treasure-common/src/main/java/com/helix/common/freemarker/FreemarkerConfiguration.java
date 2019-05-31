package com.helix.common.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author lijianyu
 * @date 2018/10/15 15:03
 */
public class FreemarkerConfiguration {

    private static Configuration cfg;

    //模板存放目录路径
    private static String templateDirPath = "/com/helix/common/freemarker";

    static{
        cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerConfiguration.class, templateDirPath));
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * 根据模板文件名，来处理文件中的内容
     *
     * @param templateName 模板名字
     * @param data         模板根 用于在模板内输出结果集。如果为map对象则没有model值
     * @param out          输出对象 具体输出到哪里
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void processTemplateFile(String templateName, Object data, Writer out) throws TemplateException, IOException {
        Object dataModel = data;
        cfg.getTemplate(templateName).process(dataModel, out);
        out.flush();
    }


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
