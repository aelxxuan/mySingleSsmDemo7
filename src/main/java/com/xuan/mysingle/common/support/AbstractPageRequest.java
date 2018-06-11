/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.support;

import java.io.Serializable;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public abstract class AbstractPageRequest implements Pageable, Serializable {
    private static final long serialVersionUID = -8254488760821506588L;
    protected static final int DEFAULT_PAGE_SIZE = 20;
    private final long page;
    private final int size;

    public AbstractPageRequest(long page) {
        if(page < 0L) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else {
            this.page = page;
            this.size = 20;
        }
    }

    public AbstractPageRequest(long page, int size) {
        if(page < 0L) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if(size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.page = page;
            this.size = size;
        }
    }

    public int getPageSize() {
        return this.size;
    }

    public long getPageNumber() {
        return this.page;
    }

    public long getOffset() {
        return this.page * (long)this.size;
    }

    public boolean hasPrevious() {
        return this.page > 0L;
    }

    public Pageable previousOrFirst() {
        return this.hasPrevious()?this.previous():this.first();
    }

    public abstract Pageable next();

    public abstract Pageable previous();

    public abstract Pageable first();

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (int)this.page;
        result1 = 31 * result1 + this.size;
        return result1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj != null && this.getClass() == obj.getClass()) {
            AbstractPageRequest other = (AbstractPageRequest)obj;
            return this.page == other.page && this.size == other.size;
        } else {
            return false;
        }
    }
}
