package com.windcoder.qycms.file.entity;

import com.windcoder.qycms.system.entity.Auditable;
import java.util.Date;

public class FileMeta extends Auditable {
    private Long id;

    private String originFileName;

    private String fname;

    private Long fsize;

    private String extention;

    private String mimeType;

    private String fhash;

    private String fmd5;

    private String relativePath;



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
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append("]");
        return sb.toString();
    }
}