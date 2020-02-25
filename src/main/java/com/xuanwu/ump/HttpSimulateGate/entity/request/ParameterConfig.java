package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 * name CDATA #REQUIRED
 * description CDATA #IMPLIED
 * defaultValue CDATA #IMPLIED
 * type (STRING|INT|LIST|FILE) "STRING"
 * required (true|false) "false"
 * example CDATA #IMPLIED
 * validateRegex CDATA #IMPLIED
 */
@XStreamAlias("parameter")
public class ParameterConfig {
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    @XStreamAsAttribute
    @XStreamAlias("description")
    private String description;
    @XStreamAsAttribute
    @XStreamAlias("defaultValue")
    private String defaultValue;
    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;
    @XStreamAsAttribute
    @XStreamAlias("required")
    private String required;
    @XStreamAsAttribute
    @XStreamAlias("example")
    private String example;
    @XStreamAsAttribute
    @XStreamAlias("validateRegex")
    private String validateRegex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getValidateRegex() {
        return validateRegex;
    }

    public void setValidateRegex(String validateRegex) {
        this.validateRegex = validateRegex;
    }
}
