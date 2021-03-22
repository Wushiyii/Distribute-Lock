package com.wushiyii.model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author: wgq
 * @Date: 2021/2/22 15:11
 */
public class EtcdNode {

    private String key;
    private Long createdIndex;
    private Long modifiedIndex;
    private String value;
    private String expiration;
    private Integer ttl;
    private Boolean dir;
    private List<EtcdNode> nodes;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCreatedIndex() {
        return createdIndex;
    }

    public void setCreatedIndex(Long createdIndex) {
        this.createdIndex = createdIndex;
    }

    public Long getModifiedIndex() {
        return modifiedIndex;
    }

    public void setModifiedIndex(Long modifiedIndex) {
        this.modifiedIndex = modifiedIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Boolean isDir() {
        return dir;
    }

    public void setDir(Boolean dir) {
        this.dir = dir;
    }

    public List<EtcdNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<EtcdNode> nodes) {
        this.nodes = nodes;
    }
}
