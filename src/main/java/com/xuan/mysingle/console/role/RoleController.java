/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.role;

import com.xuan.mysingle.common.application.Application;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.log.support.Loggable;
import com.xuan.mysingle.common.role.Role;
import com.xuan.mysingle.common.role.RoleVo;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.console.support.ResponseVo;
import com.xuan.mysingle.core.application.ApplicationService;
import com.xuan.mysingle.core.role.RoleServcie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/8
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServcie roleServcie;
    @Autowired
    private ApplicationService applicationService;

    /**
     * 显示列表首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getView(Model model){
        List<Application> apps= applicationService.getApplications();
        model.addAttribute("apps",apps);
        return "/role/list";
    }

    /**
     * 根据条件分页查询
     * @param model
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(Model model, @RequestParam String name,@RequestParam Integer pageIndex,@RequestParam Integer pageSize) {
        Pager pager = new Pager(pageIndex,pageSize);
        List<Role> list= roleServcie.getRoleByPage(name,pager);
        List<RoleVo> roleVo = new ArrayList<>();
        RoleToRoleVo(list,roleVo);
        model.addAttribute("list",roleVo);
        model.addAttribute("pager",pager);
        return "/role/content";
    }

     private void RoleToRoleVo(List<Role> roleList,List<RoleVo> voList){
         RoleVo roleVo =null;
         for(Role role:roleList){
             roleVo =new RoleVo();
             BeanUtils.copyProperties(role,roleVo);
             roleVo.setApplicationName(applicationService.getNameById(role.getApplicationId()));
             voList.add(roleVo);
         }

     }

    /**
     * 添加
     *
     * @param role 角色实体
     * @return
     */
    @Loggable(logType = LogType.DATA,value="添加角色：#{#role}")
    @RequestMapping(value="/addrole",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addRole(@Valid Role role){
        ResponseVo vo = new ResponseVo();
        try{
            boolean result= roleServcie.addRole(role);
            if(!result){
                vo.setMessage("添加失败");
            }
            vo.setSuccess(result);
        }catch (Exception e){
            vo.setSuccess(false);
            vo.setMessage("数据库错误，添加失败");
        }
        return vo;
    }

    /**
     * 根据id删除角色
     * @param ids 以逗号分隔的id字符串
     * @return
     */
    @Loggable(logType = LogType.DATA,value="删除角色：#{#ids}")
    @RequestMapping(value = "/deleterole",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo deleteRole(@RequestParam String ids){
        ResponseVo vo = new ResponseVo();
        try{
            boolean bool= roleServcie.deleteByIds(ids);
            if(!bool){
                vo.setMessage("删除失败");
            }
            vo.setSuccess(bool);
        }catch (Exception e){
            vo.setSuccess(false);
            vo.setMessage("数据库异常，删除失败");
        }
        return vo;
    }

    /**
     * 修改
     * @param role 角色实体
     * @return
     */
    @Loggable(logType = LogType.DATA,value="修改角色：#{#role}")
    @RequestMapping(value = "/updaterole",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo updateRole(@Valid Role role){
        ResponseVo vo = new ResponseVo();
        try{
            boolean res= roleServcie.updateRole(role);
            if(!res){
                vo.setMessage("修改失败");
            }
            vo.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            vo.setSuccess(false);
            vo.setMessage("数据库异常，插入失败");
        }
        return vo;
    }



}
