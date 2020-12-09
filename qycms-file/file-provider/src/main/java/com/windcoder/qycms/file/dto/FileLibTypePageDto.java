package com.windcoder.qycms.file.dto;

import com.windcoder.qycms.dto.PageDto;
import lombok.Data;

@Data
public class FileLibTypePageDto extends PageDto {
    private String searchText;
    private Boolean status;
}
