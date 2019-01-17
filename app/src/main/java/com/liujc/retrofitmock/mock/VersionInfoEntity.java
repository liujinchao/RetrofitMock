package com.liujc.retrofitmock.mock;

import java.io.Serializable;

public class VersionInfoEntity implements Serializable{
    String version;
    String url;
    String description;
    boolean forceUpdate;


    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VersionInfoEntity{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", forceUpdate=" + forceUpdate +
                '}';
    }
}
