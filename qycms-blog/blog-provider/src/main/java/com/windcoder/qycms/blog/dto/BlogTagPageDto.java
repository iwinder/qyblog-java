package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.dto.PageDto;
import lombok.Data;

@Data
public class BlogTagPageDto extends PageDto {
    private String searchText;
}
