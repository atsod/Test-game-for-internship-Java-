package com.game.entity;

import com.game.controller.PlayerOrder;
import org.springframework.data.domain.Sort;

public class PlayerPage {
    private int pageNumber = 0;
    private int pageSize = 3;
    private Sort.Direction direction = Sort.Direction.ASC;
    private String sortBy = PlayerOrder.ID.getFieldName();

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
