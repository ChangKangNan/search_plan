package org.ckn.sp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ckn
 * @date 2022/8/5
 */
@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> list;
    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private boolean hasPreviousPage = false;
    private boolean hasNextPage = false;
    private int prePage;
    private int nextPage;

    public PageInfo() {
    }

    public PageInfo(List<T> data, Integer pageNum, Integer pageSize, Integer totalCount) {
        this.list = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize == null ? totalCount + 1 : pageSize;
        this.total = totalCount;
        this.pages = pageSize == null ? 1 : ((int) Math.ceil((double) totalCount / pageSize));
        if (pageNum < pages) {
            this.hasNextPage = true;
            this.nextPage = pageNum + 1;
        }
        if (pageNum > 1) {
            this.hasPreviousPage = true;
            this.prePage = pageNum - 1;
        }
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "list=" + list +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", hasPreviousPage=" + hasPreviousPage +
                ", hasNextPage=" + hasNextPage +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                '}';
    }
}
