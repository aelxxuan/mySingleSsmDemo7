/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.product;

import com.xuan.mysingle.common.application.Application;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.log.support.Loggable;
import com.xuan.mysingle.common.product.Product;
import com.xuan.mysingle.common.role.Role;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.console.support.ResponseVo;
import com.xuan.mysingle.core.application.ApplicationService;
import com.xuan.mysingle.core.product.ProductService;
import com.xuan.mysingle.core.role.RoleServcie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品管理
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    RoleServcie roleServcie;
    @Autowired
    ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getView(Model model){
        List<Application>  applications =applicationService.getApplications();
        List<Role> roles = roleServcie.getAllRoles();
        model.addAttribute("apps",applications);
        model.addAttribute("roles",roles);
        return "/product/list";
    }

    /**
     * 分页查询
     * @param model
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model,@RequestParam String name,@RequestParam int pageIndex,@RequestParam int pageSize){
        if(!StringUtils.isEmpty(name)){
            name = name.trim();
        }
        Pager pager = new Pager(pageIndex,pageSize);
        List<Product> products = productService.getProductByParam(name,pager);
        ProductVo productVo=null;
        List<ProductVo> voList = new ArrayList<>();
        for(Product product:products){
            productVo = new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            assemblyRealName(productVo);
            voList.add(productVo);
        }
        model.addAttribute("products",voList);
        model.addAttribute("pager",pager);
        return "/product/content";
    }

    private void assemblyRealName(ProductVo productVo){
        productVo.setApplicationName(applicationService.getNameById(productVo.getApplicationId()));
        productVo.setRoleName(roleServcie.getRoleNameById(productVo.getRoleId()));
    }

    /**
     * 添加
     * @param product
     * @return
     */
    @Loggable(logType = LogType.DATA,value="添加产品：#{#product}")
    @ResponseBody
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseVo addProduct(@Valid Product product){
        ResponseVo vo = new ResponseVo();
        try{
            boolean bool =  productService.addProduct(product);
            if(!bool){
                vo.setMessage("插入失败");
            }
            vo.setSuccess(bool);
        }catch (Exception e){
            vo.setSuccess(false);
            vo.setMessage("数据库错误，请稍后重试");
        }
        return vo;
    }

    /**
     * 删除
     * @param productId
     * @return
     */
    @Loggable(logType = LogType.DATA,value="删除产品：#{#productId}")
    @ResponseBody
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseVo deleteProduct(@RequestParam String productId){
        ResponseVo vo = new ResponseVo();
        try{
            productService.deleteProductById(productId);
            vo.setSuccess(true);
        }catch (Exception e){
            vo.setSuccess(false);
            vo.setMessage("插入失败或数据库异常，请稍后重试");
        }
        return vo;
    }

    /**
     * 添加
     * @param product
     * @return
     */
    @Loggable(logType = LogType.DATA,value="修改产品：#{#product}")
    @ResponseBody
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    public ResponseVo editProduct(@Valid Product product){
        ResponseVo vo = new ResponseVo();
        try{
            boolean bool =  productService.updateProduct(product);
            if(!bool){
                vo.setMessage("修改失败");
            }
            vo.setSuccess(bool);
        }catch (Exception e){
            vo.setSuccess(false);
            vo.setMessage("数据库错误，请稍后重试");
        }
        return vo;
    }





}
