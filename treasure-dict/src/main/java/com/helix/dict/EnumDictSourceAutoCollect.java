package com.helix.dict;

import com.helix.dict.annotation.DictEnumSource;
import com.helix.dict.source.EnumDictSource;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据包名自动收集Enum类型字典数据
 * @author ljy
 * @date 2019/7/17 11:47
 */
public class EnumDictSourceAutoCollect {

    private String packagePath = null;

    private static Logger logger = LoggerFactory.getLogger(EnumDictSourceAutoCollect.class);

    public void enumDictCollect(){
        logger.debug("加载路径{}下面的枚举类",packagePath);

        if(packagePath == null){
            return;
        }

        ClassInfoList classInfoList = scanEnumDictSourceClass(getPackagePath());

        for (ClassInfo enumClassInfo : classInfoList) {
            Class enumClass = enumClassInfo.loadClass(true);

            if(enumClass == null){
                logger.warn("加载{}失败",enumClass.getName());
            }else if(!enumClassInfo.isEnum()){
                logger.warn(enumClass.getName()+"不是枚举，只支持枚举字典数据自动收集");
            }else if(enumClassInfo.isEnum()){
                logger.debug("加载枚举{}字典数据",enumClass.getName());
                EnumDictSource.INSTANCE.loadEnumData((Class<? extends Enum>) enumClassInfo.loadClass());
            }
        }
    }

    /**
     * @param packagePath 从该路径，包括子路径下搜索class文件
     */
    private ClassInfoList scanEnumDictSourceClass(String packagePath){
        ScanResult scanResult = new ClassGraph().enableAllInfo().whitelistPackages(packagePath).scan();
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(DictEnumSource.class.getName());
        return classInfoList;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}
