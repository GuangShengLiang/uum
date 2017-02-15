<#include "../layout/head.ftl">
<title>用户管理</title>

<link rel="stylesheet" type="text/css" href="/public/plugins/easyui/jquery-easyui-1.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="/public/plugins/easyui/jquery-easyui-1.5/themes/icon.css">
<script src="/public/plugins/easyui/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script src="/public/plugins/easyui/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>

<body ng-app="accountApp" ng-controller="accountCtrl">

<div class="container">
    <div class="row">
        <h2></h2>
    </div>
    <div class="row">
        <div class="form-inline">
            <div class="input-group">
                <div class="input-group-addon">UID:</div>
                <input ng-model="queryParam.id" class="form-control" placeholder="UID">
            </div>
            <div class="input-group">
                <div class="input-group-addon">用户状态:</div>
                <select class="form-control" ng-model="queryParam.status">
                    <option value="">--</option>
                    <option value="1">有效</option>
                    <option value="0">过期</option>
                </select>
            </div>
            <button type="button" class="btn btn-success" ng-click="search()">Search</button>
        </div>

    </div>
    <div class="row">
        <h2></h2>
    </div>
    <div class="row">
        <div id="tb">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" ng-click="openAdd()">新增</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" ng-click="openEdit()">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" ng-click="openEditRole()">角色</a>

        </div>
        <div style="margin-bottom: 100px;">
            <table id="dg" class="easyui-datagrid" iconCls="icon-save" toolbar="#tb">
                <thead>
                <tr>
                    <th data-options="field:'id',width:100">UID</th>
                    <th data-options="field:'name',width:100">名称</th>
                    <th data-options="field:'nameStatus',width:100">状态</th>
                    <th data-options="field:'ctimeStr',width:100">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>

    </div>

    <div id="w" class="easyui-window" title="新增用户" data-options="iconCls:'icon-save'" closed="true"
         style="width:800px;height:500px;padding:1px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <div class="form-inline" style="margin-top: 40px;margin-left: 50px">
                    <div class="input-group">
                        <div class="input-group-addon">name:</div>
                        <input ng-model="account.name" class="form-control">
                    </div>
                    <div class="input-group">
                        <div class="input-group-addon">password:</div>
                        <input ng-model="account.password" class="form-control">
                    </div>
                </div>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <div class="easyui-linkbutton" data-options="iconCls:'icon-ok'" ng-click="save()" style="width:80px">保存
                </div>
                <div class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" ng-click="close('#w')"
                     style="width:80px">
                    取消
                </div>
            </div>
        </div>
    </div>

    <div id="roleWindow" class="easyui-window" title="菜单权限" data-options="iconCls:'icon-save'" closed="true"
         style="width:800px;height:500px;padding:1px;">
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <h3>已有角色</h3>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <td>ID</td>
                            <td>角色名称</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="role in roleList" ng-model="roleList">
                            <td>{{role.id}}</td>
                            <td>{{role.name}}</td>
                            <td><a href="#" ng-click="removeRoleFromAccount(role.id)">删除</a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-2">

                    <h3>操作</h3>
                    <div class="col-xs-9">
                        <div ng-click="addRoleToAccount()" class="btn btn-primary">添加</div>
                    </div>
                    <#--<div class="col-xs-9">
                        <div ng-click="removeRoleFromAccount()" class="btn btn-danger">删除</div>
                    </div>-->
                </div>
                <div class="col-md-3">
                    <h3>角色列表</h3>
                    <div id="treeview_list" class=""></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/public/plugins/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<script>

    var app = angular.module("accountApp", []);
    app.controller("accountCtrl", function ($scope, $http) {
        $scope.queryParam = {};

        $scope.search = function () {
            $('#dg').datagrid({
                method: "GET",
                url: '/account',
                queryParams: $scope.queryParam,
                scrollbarSize: 50,
                fitColumns: true,
                rownumbers: true,
                singleSelect: true,
                pagination: true,
                pageList: [20, 50, 100, 200],
                /* columns: [[
                     {field: 'id', title: 'UID', width: 100},
                     {field: 'name', title: '名称', width: 100},
                     {field: 'statusName', title: '状态', width: 100},
                     {field: 'ctimeStr', title: '创建时间', width: 100}
                 ]],*/
                onSelect: function(rowIndex, rowData){
                    $scope.account = rowData;
                },
                onLoadError: function () {

                }
            });
        };
        $scope.save = function () {
            $http.post('/account', JSON.stringify($scope.account)).success(function (data, status, headers, config) {

//                swal("Good!", "", "success");

                $scope.close();

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");

            });
        };
        $scope.openAdd= function () {

            $scope.account = {};

            $('#w').window('open');

        };
        $scope.openEdit = function () {

//            $scope.account = $('#dg').datagrid('getSelected');

            $('#w').window('open');

        };
        $scope.openEditRole = function () {

            if(!$scope.loaded){
                 $scope.loadRoleTree();
            }
            $scope.loadRoleOfAccount();

            $('#roleWindow').window('open')

        };
        $scope.loadRoleOfAccount = function () {

//            $scope.account = $('#dg').datagrid('getSelected');

            $http.get('/account/role/'+$scope.account.id).success(function (data, status, headers, config) {
                $scope.roleList=data;
//                $scope.$apply();


            }).error(function (data, status, headers, config) {

            });
        };

        $scope.loadRoleTree = function () {

            $http.get('/role').success(function (data, status, headers, config) {
                $scope.loaded = true;
                $('#treeview_list').treeview({
                    color: "#428bca",
                    data: data,
                    levels: 5,
                    onNodeSelected: function (event, data) {
                        $scope.role = data;
                    }
                });
            }).error(function (data, status, headers, config) {

            });
        };
        $scope.addRoleToAccount = function () {
            if ($scope.check_not_selected("#treeview_list")) {
                swal("Oops...", "选择角色!", "info");
                return
            }
            var accountRole = {uid: $scope.account.id, roleId: $scope.role.id}

            $http.post('/account/role', JSON.stringify(accountRole)).success(function (data, status, headers, config) {

                $scope.loadRoleOfAccount();
//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };
        $scope.removeRoleFromAccount = function (roleId) {
 /*           if ($scope.check_not_selected("#treeview_own")) {
                swal("Oops...", "选择菜单树!", "info");
                return
            }*/

            $http.delete('/account/' + $scope.account.id + '/role/' + roleId).success(function (data, status, headers, config) {

                $scope.loadRoleOfAccount();

//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };
        $scope.close = function (id) {
            $(id).window('close')
        };
        $scope.check_not_selected = function (tree_id) {
            var selecteds = $(tree_id).treeview('getSelected');
            return selecteds.length == 0;
        };
    });
</script>
<#include "../layout/footer.ftl">