/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.support;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class PageRequest extends AbstractPageRequest {
    private static final long serialVersionUID = 6489359158841475267L;
    private final Sort sort;

    public PageRequest(long page) {
        super(page);
        this.sort = null;
    }

    public PageRequest(long page, int size) {
        this(page, size, (Sort)null);
    }

    public PageRequest(long page, int size, Sort.Direction direction, String... properties) {
        this(page, size, new Sort(direction, properties));
    }

    public PageRequest(long page, int size, Sort.Direction direction, Column... properties) {
        this(page, size, new Sort(direction, properties));
    }

    public PageRequest(long page, int size, Sort sort) {
        super(page, size);
        this.sort = sort;
    }

    public PageRequest(long page, Sort sort) {
        super(page);
        this.sort = sort;
    }

    public Sort getSort() {
        return this.sort;
    }

    public Pageable next() {
        return new PageRequest(this.getPageNumber() + 1L, this.getPageSize(), this.getSort());
    }

    public PageRequest previous() {
        return this.getPageNumber() == 0L?this:new PageRequest(this.getPageNumber() - 1L, this.getPageSize(), this.getSort());
    }

    public Pageable first() {
        return new PageRequest(0L, this.getPageSize(), this.getSort());
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(!(obj instanceof PageRequest)) {
            return false;
        } else {
            PageRequest that = (PageRequest)obj;
            boolean sortEqual = this.sort == null?that.sort == null:this.sort.equals(that.sort);
            return super.equals(that) && sortEqual;
        }
    }

    public int hashCode() {
        return 31 * super.hashCode() + (null == this.sort?0:this.sort.hashCode());
    }

    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", new Object[]{Long.valueOf(this.getPageNumber()), Integer.valueOf(this.getPageSize()), this.sort == null?null:this.sort.toString()});
    }

    public static int getDefaultPageSize() {
        return 20;
    }

}
