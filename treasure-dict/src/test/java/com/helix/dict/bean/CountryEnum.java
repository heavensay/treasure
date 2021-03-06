package com.helix.dict.bean;

import com.helix.dict.annotation.EnumDictSourceMetadata;

/**
 * @author ljy
 * @date 2019/7/5 18:27
 */
@EnumDictSourceMetadata(valueLabelFieldName = "text",valueFieldName = "code")
public enum CountryEnum {

    A("zh","中国"),
    B("am","美国"),
    ;

    private String code;
    private String text;

    CountryEnum(String code, String text){
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
