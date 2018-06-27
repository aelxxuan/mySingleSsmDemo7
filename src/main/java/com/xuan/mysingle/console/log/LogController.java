/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.log;

import com.xuan.mysingle.core.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/27
 */
@Controller
public class LogController {
    @Autowired
    LogService logService;
}
