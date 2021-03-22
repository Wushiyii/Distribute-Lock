package com.wushiyii.model;

import com.alibaba.fastjson.JSON;

/**
 * @Author: wgq
 * @Date: 2021/2/22 15:28
 */
public class EtcdResponse {

    private String action;
    private EtcdNode node;
    private EtcdNode prevNode;

    private Integer errorCode;
    private String message;
    private String cause;
    private Integer errorIndex;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public EtcdNode getNode() {
        return node;
    }

    public void setNode(EtcdNode node) {
        this.node = node;
    }

    public EtcdNode getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(EtcdNode prevNode) {
        this.prevNode = prevNode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getErrorIndex() {
        return errorIndex;
    }

    public void setErrorIndex(Integer errorIndex) {
        this.errorIndex = errorIndex;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
