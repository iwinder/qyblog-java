package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.dto.PageDto;
import lombok.Data;

@Data
public class BlogArticlePageDto extends PageDto {
    private String searchText;
    private Boolean published;
    private Integer type;
}
