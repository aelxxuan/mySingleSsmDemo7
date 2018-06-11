<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--管理员列表去掉姓名，并且span标签改为label标签 xuanzongjun 2017-1-13 -->
<!--suppress ALL -->
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a style="margin-right:30px;" class="navbar-brand" href="../index"><i class="fa fa-university"
                                                                              style="color:#EB4F38;"></i>
            <strong><span>学科网学校自助服务系统1.1</span></strong> </a>
        <button type="button" class="navbar-toggle menu-control" data-toggle="collapse"
                data-target=".navbar-ex1-collapse">
            <span class="sr-only">collapse</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-top-links navbar-right">

        <li class="dropdown" style="width: 180px">
            <a class="dropdown-toggle" data-toggle="dropdown" href="" aria-expanded="true">
                <i class="fa fa-user fa-fw"></i>&nbsp;&nbsp;<span id="name">xkw_123456  </span>&nbsp;&nbsp;<i
                    class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <div class="dropdown-user-info">
                        <p class="text-success">超级管理员</p>
                        <p class="text-muted"><label>ID：</label><span></span></p>
                        <p class="text-muted"><label>账号：</label><span>保密</span></p>
                        <p class="text-muted"><label>学校：</label><span>保密</span></p>
                        <p class="text-muted"><label>邮箱：</label><span>email@email.com</span></p>
                        <p class="text-muted"><label>手机：</label><span>13333333333</span></p>
                    </div>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <li title="注销">
            <a id="logoutLink" href="">
                <i class="glyphicon glyphicon-log-in"></i> 注销
            </a>
        </li>
    </ul>

    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="navbar-default sidebar navbar-static-side" role="navigation">
        <div class="sidebar-nav sidebar-collapse">
            <ul class="nav in collapse" id="side-menu11">
                <li class="">
                    <a url="../load" id="defaultLoad">
                        <i class="home"></i><span class="nav-label">首页</span></a>
                </li>
                <li>
                    <a>
                        <i class="fa fa-fw fa-gears"></i>
                        <span class="nav-label">系统管理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level collapse">
                        <li>
                            <a url="../application"><i class="fa fa-files-o"></i>应用管理</a>
                        </li>
                        <li>
                            <a url="../role/index"><i class="fa fa-users"></i>角色管理</a>
                        </li>
                        <li>
                            <a url="../product"><i class="fa fa-users"></i>产品管理</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a>
                        <i class="fa fa-fw fa-gears"></i>
                        <span class="nav-label">用户管理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level collapse">
                        <li>
                            <a url="../user/getlist"><i class="fa fa-files-o"></i>用户列表</a>
                        </li>
                        <li>
                            <a url="../userRole"><i class="open_s"></i>用户与角色</a>
                        </li>
                        <li>
                            <a url="../school"><i class="fa fa-users"></i>学校管理</a>
                        </li>

                    </ul>
                </li>
                <li>
                    <a>
                        <i class="product_m"></i>
                        <span class="nav-label">日志管理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level collapse">
                        <li>
                            <a url="../user/getlist"><i class="fa fa-files-o"></i>系统日志</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
</nav>
