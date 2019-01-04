package com.zy.graduation1.common;

import java.util.ArrayList;
import java.util.List;

public class MyPage<T> {

    private Long total;
    private List<T> list;

    public MyPage() {
        this.total = 0L;
        this.list = new ArrayList<>();
    }

    public Long getTotal() {
        return this.total;
    }

    public List<T> getList() {
        return this.list;
    }

    public MyPage(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }

    public void setList(final List<T> list) {
        this.list = list;
    }
}
