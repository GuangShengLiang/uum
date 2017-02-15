<#include "../layout/head.ftl">
<title>角色管理</title>

<body ng-app="roleApp" ng-controller="roleCtrl" ng-init="initData()">
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <h2>角色树</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div id="treeview4" class=""></div>
        </div>
        <div class="col-md-8">

            <div class="col-xs-12">
                <div class="btn btn-primary" ng-click="save()">保存</div>
                <div class="btn btn-primary" ng-click="add_root_branch()">新增根角色</div>
                <div class="btn btn-primary" ng-click="try_adding_a_branch()">新增子角色</div>
                <div class="btn btn-danger" ng-click="remove_branch()">删除</div>
            </div>
            <div class="col-xs-5">
                <div class="form-group">
                    <label>角色名称</label>
                    <input class="form-control" ng-model="role.name">
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" ng-model="role.status"> 是否启用
                    </label>
                </div>
            </div>
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-md-5">
                        <h3>已有菜单</h3>
                        <div id="treeview_own" class=""></div>
                    </div>
                    <div class="col-md-2">
                        <h3>操作</h3>
                        <div class="col-xs-9">
                            <div ng-click="addMenuToRole()" class="btn btn-primary">添加</div>
                        </div>
                        <div class="col-xs-9">
                            <div ng-click="removeMenuFromRole()" class="btn btn-danger">删除</div>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <h3>菜单列表</h3>
                        <div id="treeview_list" class=""></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/public/plugins/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<script>

    var app = angular.module("roleApp", []);
    app.controller("roleCtrl", function ($scope, $http) {

        $scope.initData = function () {

            $scope.load();
            $scope.loadMenuTree();
        }

        $scope.role = {};
        $scope.load = function () {
            $http.get('/role').success(function (data, status, headers, config) {

                $('#treeview4').treeview({
                    color: "#428bca",
                    data: data,
                    levels: 5,
                    onNodeSelected: function (event, data) {
                        $scope.role = data;
                        $scope.$apply();
                        $scope.loadMenuTreeOfRole();

                    }
                });
            }).error(function (data, status, headers, config) {

            });
        };

        $scope.save = function () {
            $scope.role.parentId = undefined;
            $http.put('/role/' + $scope.role.id, JSON.stringify($scope.role)).success(function (data, status, headers, config) {

                $scope.load();

//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };
        $scope.addMenuToRole = function () {
            if ($scope.check_not_selected("#treeview_list")) {
                swal("Oops...", "选择菜单树!", "info");
                return
            }
            var roleMenu = {roleId: $scope.role.id, menuId: $scope.menu.id}

            $http.post('/role/menu', JSON.stringify(roleMenu)).success(function (data, status, headers, config) {

                $scope.loadMenuTreeOfRole();

//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };
        $scope.removeMenuFromRole = function () {
            if ($scope.check_not_selected("#treeview_own")) {
                swal("Oops...", "选择菜单树!", "info");
                return
            }
            ;

            $http.delete('/role/' + $scope.role.id + '/menu/' + $scope.roleMenu.id).success(function (data, status, headers, config) {

                $scope.loadMenuTreeOfRole();

//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };

        $scope.loadMenuTreeOfRole = function () {
            $http.get('/menu/role/' + $scope.role.id).success(function (data, status, headers, config) {

                $('#treeview_own').treeview({
                    color: "#428bca",
                    data: data,
                    levels: 5,
                    onNodeSelected: function (event, data) {
                        $scope.roleMenu = data;
                        $scope.$apply();
                        $scope.loadMenuTree();
                    }
                });
            }).error(function (data, status, headers, config) {

            });
        };

        $scope.loadMenuTree = function () {
            $http.get('/menu').success(function (data, status, headers, config) {

                $('#treeview_list').treeview({
                    color: "#428bca",
                    data: data,
                    levels: 3,
                    onNodeSelected: function (event, data) {
                        $scope.menu = data;
                        $scope.$apply();
                    }
                });
            }).error(function (data, status, headers, config) {

            });
        };

        $scope.check_not_selected = function (tree_id) {
            var selecteds = $(tree_id).treeview('getSelected');
            return selecteds.length == 0;
        };

        $scope.try_adding_a_branch = function () {
            if ($scope.check_not_selected("#treeview4")) {
                swal("Oops...", "选择角色!", "info");
                return
            }
            ;
            var role = {name: "新子角色", parentId: $scope.role.id}
            $http.post('/role', JSON.stringify(role)).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };
        $scope.add_root_branch = function () {
            var role = {name: "新根角色", status: true}
            $http.post('/role', JSON.stringify(role)).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };

        $scope.remove_branch = function () {

            if ($scope.check_not_selected("#treeview4")) {
                swal("Oops...", "选择角色!", "info");
                return
            }
            ;

            $http.delete('/role/' + $scope.role.id).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };
    });
</script>
<#include "../layout/footer.ftl">