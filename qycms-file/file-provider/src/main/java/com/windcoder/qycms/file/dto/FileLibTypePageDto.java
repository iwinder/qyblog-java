package com.windcoder.qycms.file.dto;

import com.windcoder.qycms.dto.PageDto;
import lombok.Data;


public class FileLibTypePageDto extends PageDto {
    private String searchText;
    private Boolean status;


    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
