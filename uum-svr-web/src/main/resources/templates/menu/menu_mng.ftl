<#include "../layout/head.ftl">
<title>菜单管理</title>

<body ng-app="menuApp" ng-controller="menuCtrl">
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <h2>菜单树</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <div id="treeview4" class=""></div>
        </div>
        <div class="col-sm-4">
            <div>
                <div ng-click="add_root_branch()" class="btn btn-primary">新增根菜单</div>
                <div ng-click="try_adding_a_branch()" class="btn btn-primary">新增子菜单</div>
                <div ng-click="remove_branch()" class="btn btn-danger">删除</div>
            </div>
            <div>
                <div class="form-group">
                    <label>菜单名称</label>
                    <input class="form-control" ng-model="menu.name">
                </div>
                <div class="form-group">
                    <label>url地址</label>
                    <input class="form-control" ng-model="menu.tpl">
                </div>

                <div class="checkbox">
                    <label>
                        <input type="checkbox" ng-model="menu.status"> 是否启用
                    </label>
                </div>
                <button class="btn btn-primary" ng-click="save()">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="/public/plugins/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<script>

    var app = angular.module("menuApp", []);
    app.controller("menuCtrl", function ($scope, $http) {
        $scope.menu = {};
        $scope.load = function () {
            $http.get('/menu').success(function (data, status, headers, config) {

                $('#treeview4').treeview({
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
        $scope.load();

        $scope.save = function () {
            $scope.menu.parentId = undefined;
            $http.put('/menu/' + $scope.menu.id, JSON.stringify($scope.menu)).success(function (data, status, headers, config) {

                $scope.load();

//                swal("Good!", "", "success");

            }).error(function (data, status, headers, config) {
                swal("Oops...", "操作失败!", "error");
            });
        };

        $scope.try_adding_a_branch = function () {
            var menu = {name: "新菜单", parentId: $scope.menu.id}
            $http.post('/menu', JSON.stringify(menu)).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };
        $scope.add_root_branch = function () {
            var menu = {name: "新根菜单", status: true}
            $http.post('/menu', JSON.stringify(menu)).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };

        $scope.remove_branch = function () {

            $http.delete('/menu/' + $scope.menu.id).success(function (data, status, headers, config) {

                $scope.load();

            }).error(function (data, status, headers, config) {

            });
        };
    });
</script>
<#include "../layout/footer.ftl">