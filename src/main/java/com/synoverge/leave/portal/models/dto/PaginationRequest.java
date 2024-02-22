package com.synoverge.leave.portal.models.dto;

import com.synoverge.leave.portal.utility.SortingOrder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.*;

public class PaginationRequest {

    @NotNull(message = "Page number should not be null")
    @Min(value = 0,message = "Page size should not be less then 0")
    private int pageNumber;
    @Min(value = 0,message = "Page size should not be less then 0")
    private int itemPerPage;
    @NotBlank(message = "Soring field is mandatory")
    @NotNull(message = "Soring field is mandatory")
    private String sortingField;
    @NotNull(message = "Soring Order is mandatory")
    private SortingOrder sortingOrder;

    public PaginationRequest() {
    }

    public PaginationRequest(int pageNumber, int itemPerPage, SortingOrder sortingOrder, String sortingField) {
        this.pageNumber = pageNumber;
        this.itemPerPage = itemPerPage;
        this.sortingOrder = sortingOrder;
        this.sortingField = sortingField;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }
}
