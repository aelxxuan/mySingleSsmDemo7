/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.application;

import com.xuan.mysingle.common.application.Application;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.log.support.Loggable;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.console.support.ResponseVo;
import com.xuan.mysingle.core.application.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 应用
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/28
 */
@Controller
@RequestMapping(value = "/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getApplication() {
        return "/application/applicationList";
    }

    @RequestMapping("/searchApp")
    public String getApplicationByName(Model model, @RequestParam String name, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Pager pager = new Pager(pageIndex, pageSize);
        List<Application> list = applicationService.getApplicationByParam(name, pager);
        model.addAttribute("applications", list);
        model.addAttribute("pager", pager);
        return "/application/applicationContent";
    }

    /**
     * 添加应用
     *
     * @param application 应用
     * @return
     */
    @Loggable(logType = LogType.DATA, value = "新增应用：#{#application}")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addApplication(@Valid Application application) {
        ResponseVo vo = new ResponseVo();
        Application app = applicationService.getApplicationById(application.getId());
        if (app != null) {
            vo.setSuccess(false);
            vo.setMessage("应用ID已经存在！");
            return vo;
        }
        try {
            boolean res = applicationService.addApplication(application);
            if (!res) {
                vo.setMessage("添加失败！");
            }
            vo.setSuccess(res);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMessage("数据库异常，添加失败");
        }
        return vo;
    }

    /**
     * 修改应用
     *
     * @param application 应用实体
     * @return
     */
    @Loggable(logType = LogType.DATA, value = "修改应用：#{#application}")
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseVo editApplication(@Valid Application application) {
        ResponseVo vo = new ResponseVo();
        Application app = applicationService.getApplicationById(application.getId());
        if (app == null) {
            vo.setSuccess(false);
            vo.setMessage("应用ID不存在！");
            return vo;
        }
        try {
            boolean res = applicationService.updateApplication(application);
            if (!res) {
                vo.setMessage("修改失败！");
            }
            vo.setSuccess(res);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMessage("数据库异常，修改失败");
        }
        return vo;
    }

    @Loggable(logType = LogType.DATA, value = "删除应用#{#id}")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseVo deleteApplication(@RequestParam String id) {
        ResponseVo vo = new ResponseVo();
        try {
            boolean res = applicationService.deleteApplication(id);
            if (!res) {
                vo.setMessage("删除失败");
            }
            vo.setSuccess(res);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMessage("数据库异常");
        }
        return vo;
    }

    /**
     * 根据ID获取应用
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Application getApplicationById(@PathVariable String id) {
        Application app= applicationService.getApplicationById(id);
        if(app !=null){
            return app;
        }
        return null;
    }


}
