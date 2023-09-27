package com.windcoder.qycms.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    /**
     * 当前页码
     */
    protected int page;

    /**
     * 每页条数
     */
    protected int size = 10;

    /**
     * 总条数
     */
    protected long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 是否是最后一页
     */
    private boolean  EOFFlag;
    protected List<T> list;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageDto{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", EOFFlag=").append(EOFFlag);
        sb.append(", list=").append(list);

        sb.append('}');
        return sb.toString();
    }
}
