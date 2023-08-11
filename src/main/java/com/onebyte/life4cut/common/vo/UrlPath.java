package com.onebyte.life4cut.common.vo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class UrlPath {


    private String value;

    protected UrlPath() {
    }

    UrlPath(String value) {
        this.value = value;
    }

    public static UrlPath of(String value) {
        return new UrlPath(value);
    }

    public String getValue() {
        return value;
    }
}
