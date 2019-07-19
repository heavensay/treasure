package com.helix.common.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author lijianyu
 * @date 2018/10/15 15:03
 */
public class FreemarkerUtil {

    private static Configuration commonCfg = FreemarkerUtil.Conifg.getCommonCfg();

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
        commonCfg.getTemplate(templateName).process(dataModel, out);
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
        Template t = new Template(null, templateContent, commonCfg);
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
        Template t = new Template(null, templateContent, commonCfg);
        t.process(dataModel, sw);
        sw.close();
        return sw.toString();
    }

    /**
     * 根据传入的模板内容来处理,并返回
     * @param templateContent
     * @param data
     * @param cfg
     * @return 处理好之后的模板内容
     * @throws TemplateException
     * @throws IOException
     */
    public static String getProcessedContent(String templateContent, Object data,Configuration cfg) throws TemplateException, IOException {
        Object dataModel = data != null ? data : new Object();

        StringWriter sw = new StringWriter();
        Template t = new Template(null, templateContent, cfg);
        t.process(dataModel, sw);
        sw.close();
        return sw.toString();
    }

    public static class Conifg{

        private static Configuration commonCfg;

        private static String DEFAULT_TEMPLATE_FILE_NAME = "/template";

        static{
            commonCfg = new Configuration(Configuration.VERSION_2_3_22);
            commonCfg.setDefaultEncoding("UTF-8");
            try {
                //默认为class目录下的template目录作为模板路径
                String classBasePath = Conifg.class.getResource("/").getPath();
                File file = new File(classBasePath+ DEFAULT_TEMPLATE_FILE_NAME);
                if(file.exists()){
                    commonCfg.setDirectoryForTemplateLoading(file);
                }else{
                    commonCfg.setDirectoryForTemplateLoading(new File(classBasePath));
                }
            } catch (IOException e) {
                //do nothing
            }
        }

        protected static Configuration getCommonCfg() {
            return commonCfg;
        }

        public static void setTemplateDirPath(String templateDirPath) {
            File file = new File(templateDirPath);
            try {
                commonCfg.setDirectoryForTemplateLoading(file);
            } catch (IOException e) {
                throw new RuntimeException("模板目录路径错误",e);
            }
        }
    }
}
