package com.helix.dict.source;

import com.helix.dict.DictEnumSourceHelper;
import com.helix.dict.DictKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 便捷收集Enum字典数据
 * @author ljy
 * @date 2019/7/9 21:35
 */
public class EnumDictSource extends AbstarctDictSource {

    public final static EnumDictSource INSTANCE = new EnumDictSource();

    private EnumDictSource(){}

    private List<Class<? extends Enum>> clazzList = new ArrayList();

    @Override
    public void loadData() {
        clazzList.stream().forEach(enumClass->{
            loadEnumData(enumClass);
        });
    }

    /**
     * 存储枚举字典数据
     * @param enumClass
     */
    public void loadEnumData(Class<? extends Enum> enumClass){
        if(!clazzList.contains(enumClass)){
            clazzList.add(enumClass);
        }
        Map<DictKey,Object> map = DictEnumSourceHelper.resolveEnumSource(enumClass);
        getDictData().putAll(map);
    }
}
