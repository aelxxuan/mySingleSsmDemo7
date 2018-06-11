/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/20
 */
public class Pager {

    public Pager() {
    }

    public Pager(int page, int step) {
        this.page = page;
        this.pageSize = step;
    }

    /**
     * 当前页
     */
    private int page;
    /**
     * 每页显示记录数
     */
    private int pageSize;
    /**
     * 总的记录数
     */
    private int totalRow;
    /**
     * 总的页数
     */
    private int totalPage;

    private int start;

    /**
     * 分页界面上显示的最小值
     */
    private int startDisplay;
    /**
     * 分页界面上显示的最大值
     */
    private int endDisplay;
    /**
     * 左边...要跳转到的页面
     */
    private int leftToPage;
    /**
     * 右边...要跳转到的页面
     */
    private int rightToPage;

    public int getLeftToPage() {
        return leftToPage;
    }

    public void setLeftToPage(int leftToPage) {
        this.leftToPage = leftToPage;
    }

    public int getRightToPage() {
        return rightToPage;
    }

    public void setRightToPage(int rightToPage) {
        this.rightToPage = rightToPage;
    }


    public int getStartDisplay() {
        if (startDisplay == 0) {
            computeDispaly();
        }
        return startDisplay;
    }

    //计算展示页
    private void computeDispaly() {
        //展示5个
        int count = 5;
        //3
        int middle = (count - 1) / 2 + 1;
        //2
        int leftOrRight = (count - 1) / 2;
        if (totalPage <= count) {
            startDisplay = 1;
            endDisplay = totalPage;
        } else {
            startDisplay = page - leftOrRight;
            endDisplay = page + leftOrRight;
            if (startDisplay < 1) {
                startDisplay = 1;
                endDisplay = count;
            }
            if (endDisplay > totalPage) {
                startDisplay = totalPage - (count - 1);
                endDisplay = totalPage;
            }
            //有左边...
            if (startDisplay > 1) {
                //左边够显示的 直接出count个
                if (startDisplay > count) {
                    //  当前页减少count
                } else {
                    leftToPage = page - count;
                    //左边到头了
                    leftToPage = 1;
                }
            }
            //有右边...
            if (endDisplay < totalPage) {
                //右边够显示的
                if (totalPage >= (endDisplay + count)) {
                    //  当前页增加count
                    rightToPage = page + count;
                } else {
                    rightToPage = totalPage;
                }
            }
        }
    }

    public void setStartDisplay(int startDisplay) {
        this.startDisplay = startDisplay;
    }

    public int getEndDisplay() {
        if (endDisplay == 0) {
            computeDispaly();
        }
        return endDisplay;
    }

    public void setEndDisplay(int endDisplay) {
        this.endDisplay = endDisplay;
    }

    public int getStart() {
        fresh();
        return (page - 1) * pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }


    public int getTotalPage() {
        return totalPage;
    }


    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        fresh();
        this.totalRow = totalRow;

        totalPage = this.totalRow / this.pageSize + 1;
        if ((this.totalRow % this.pageSize == 0)) {
            this.totalPage--;
        }
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public void fresh() {
        //缺省第一页，和公司的那个控件不一样 那个为0
        if (page < 1) {
            page = 1;
        }
        //分页控件缺省20
        if (pageSize < 1) {
            pageSize = Integer.valueOf(20);
        }
    }
}
