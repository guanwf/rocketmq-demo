package com.rocketmq.demo.common.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StockType implements Serializable {
    /** 创建时间 */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createdTime;
    /** 最近更新时间 */
    Date updatedTime;
    /** 库存类型标识 */
    String stockTypeId;
    /** 库存类型名称 */
    String stockTypeName;
    Integer stockTypeGroup;

    /** 配置是否正确 */
    boolean right = true;

    String errorInfo;

    public void setErrorInfo(String errorInfo) {
        this.right = false;
        this.errorInfo = errorInfo;
    }
}
