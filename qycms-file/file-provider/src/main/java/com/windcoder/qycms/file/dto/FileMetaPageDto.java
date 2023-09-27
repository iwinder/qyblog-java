package com.windcoder.qycms.file.dto;

import com.windcoder.qycms.dto.PageDto;

public class FileMetaPageDto extends PageDto {
    /**
     * 关键词
     */
    private String searchText;
    /**
     * 文档库类型ID
     */
    private Long typeId;
    /**
     * 下一次列举的marker
     */
    private String marker;
    /**
     * 列举操作是否已到所有文件列表结尾，如果为true表示无需再发送列举请求
     */
    private boolean  EOFFlag;


    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public boolean getEOFFlag() {
        return EOFFlag;
    }

    public void setEOFFlag(boolean EOFFlag) {
        this.EOFFlag = EOFFlag;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
