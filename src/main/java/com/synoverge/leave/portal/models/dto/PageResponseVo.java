package com.synoverge.leave.portal.models.dto;

public class PageResponseVo {

    private int totalPage;
    private int itemPerPage;
    private Long totalItem;
    private Object pages;

    public PageResponseVo() {
    }
    public PageResponseVo(int totalPage, int itemPerPage, Long totalItem, Object pages) {
        this.totalPage = totalPage;
        this.itemPerPage = itemPerPage;
        this.totalItem = totalItem;
        this.pages = pages;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public Long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Long totalItem) {
        this.totalItem = totalItem;
    }

    public Object getPages() {
        return pages;
    }

    public void setPages(Object pages) {
        this.pages = pages;
    }
}
