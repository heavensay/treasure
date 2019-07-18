package com.helix.dict.bean;


import com.helix.dict.annotation.EnumDictSourceMetadata;

/**
 * @author ljy
 * @date 2019/7/5 18:27
 */
@EnumDictSourceMetadata(valueLabelFieldName = "text")
public enum AnimalEnum {

    CAT("猫"),
    DOG("狗"),
    ;

    private String text;

    AnimalEnum(String text){
        this.text = text;
    }


    public String getText() {
        return text;
    }
}
