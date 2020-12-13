package com.windcoder.qycms.file.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FileMetaDto {

    /**
     * id
     */
    private Long id;

    /**
     * 原始名称
     */
    private String originFileName;

    /**
     * 文件名
     */
    private String fname;

    /**
     * 文件大小
     */
    private Long fsize;

    /**
     * 文件扩展名
     */
    private String extention;

    /**
     * 资源的 MIME 类型
     */
    private String mimeType;

    /**
     * 文件的HASH值
     */
    private String fhash;

    /**
     * 文件md5值
     */
    private String fmd5;

    /**
     * 通过浏览器访问的相对路径
     */
    private String relativePath;

    /**
     * 创建者
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifiedDate;


    /**
     * 文件的存储类型，0为普通存储，1为低频存储
     */
    public int type;
    /**
     * 文件的状态，0表示启用，1表示禁用
     */
    public int status;
    /**
     * 文件上传时设置的endUser<br/>
     * 七牛
     */
    public String endUser;

    /**
     * 域名
     */
    private String domain;

    /**
     * 文件链接
     */
    private String defUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Long getFsize() {
        return fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFhash() {
        return fhash;
    }

    public void setFhash(String fhash) {
        this.fhash = fhash;
    }

    public String getFmd5() {
        return fmd5;
    }

    public void setFmd5(String fmd5) {
        this.fmd5 = fmd5;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDefUrl() {
        return defUrl;
    }

    public void setDefUrl(String defUrl) {
        this.defUrl = defUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", originFileName=").append(originFileName);
        sb.append(", fname=").append(fname);
        sb.append(", fsize=").append(fsize);
        sb.append(", extention=").append(extention);
        sb.append(", mimeType=").append(mimeType);
        sb.append(", fhash=").append(fhash);
        sb.append(", fmd5=").append(fmd5);
        sb.append(", relativePath=").append(relativePath);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append("]");
        return sb.toString();
    }


}