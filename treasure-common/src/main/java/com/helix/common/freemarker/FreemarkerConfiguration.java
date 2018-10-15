package com.helix.common.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
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

}
