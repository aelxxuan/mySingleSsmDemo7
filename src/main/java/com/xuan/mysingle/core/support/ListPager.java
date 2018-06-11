/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.support;

import com.xuan.mysingle.console.support.Pager;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 列表分页
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/29
 */
public class ListPager<T> {
    /**
     * 将1个list结果按分页返回某一页的list信息
     *
     * @param allList 所有数据
     * @param pager   分页
     * @return 1页数据列表
     */
    public static <T> List<T> getPage(List<T> allList, Pager pager) {
        if (CollectionUtils.isEmpty(allList)) {
            return Collections.emptyList();
        }

        if (pager == null)
            pager = new Pager();
        int allSize = allList.size();
        pager.setTotalRow(allSize);

        if (pager.getStart() >= allSize) {
            return Collections.emptyList();
        }

        int toIndex = pager.getStart() + pager.getPageSize();
        if (toIndex > allSize) toIndex = allSize;

        return allList.subList(pager.getStart(), toIndex);
    }

}
