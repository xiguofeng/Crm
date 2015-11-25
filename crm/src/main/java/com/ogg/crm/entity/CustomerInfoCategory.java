package com.ogg.crm.entity;

import java.io.Serializable;

public class CustomerInfoCategory implements Serializable {

    private static final long serialVersionUID = 8912365559481657349L;

    private String defaultText;

    private String defaultValue;

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
