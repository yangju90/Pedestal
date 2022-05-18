package indi.mat.work.mq.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SQL参数数据
 */
public class MqSqlParameterBean {

    // 参数类型，项目语言相关，开发语言级别的数据类型
    // [可选]
    @JsonProperty("Type")
    private String type;
    // 数据库端参数类型定义
    // [必选]
    @JsonProperty("DBType")
    private String dbType;
    // 数据库参数的方向，向数据库传入参数为Input，读取数据的参数一般用 Output
    // [可选]
    @JsonProperty("Direction")
    private String direction;
    // 参数名称，标识该数据库操作中的一个参数
    // [必选]
    @JsonProperty("Name")
    private String name;
    // 定义的参数长度，只用于字符序列(字符串)类型
    // [可选]
    @JsonProperty("Size")
    private long size;
    // 定义的参数精度，只用于数值类型
    // [可选]
    @JsonProperty("Precision")
    private int precision;
    // 定义的参数小数位数，只用于DECIMAL类型
    // [可选]
    @JsonProperty("Scale")
    private int scale;
    // 实际的参数值，该值可能是一个较长的字符串(text/xml/json)
    // [可选]
    @JsonProperty("Value")
    private String value;

    public MqSqlParameterBean(String dbType, String name, String value) {
        this.dbType = dbType;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
