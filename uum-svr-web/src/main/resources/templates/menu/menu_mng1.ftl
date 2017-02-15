<#include "../layout/head.ftl">
<script src="/public/angular/1.5.8/angular-animate.min.js"></script>
<link href="/public/plugins/angular-tree/abn_tree.css" rel="stylesheet">
<body ng-app="menuApp" ng-controller="menuCtrl">
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <h2>Expanded</h2>
            <abn-tree tree-data="my_data"
                      tree-control="my_tree"
            <#--icon-leaf="icon-file"
            icon-expand="icon-plus-sign"
            icon-collapse="icon-minus-sign"-->
                      on-select="my_tree_handler(branch)"
                      expand-level="2">
            </abn-tree>
        </div>
        <div class="col-sm-4">
            <div ng-click="add_root_branch()" class="btn btn-default btn-sm">新增根菜单</div>
            <div ng-click="try_adding_a_branch()" class="btn btn-default btn-sm">新增子菜单</div>
            <form role="form">
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
                <button class="btn btn-default" ng-click="save()">保存</button>
            </form>

        </div>
    </div>
</div>

<script src="/public/plugins/angular-tree/abn_tree_directive.js"></script>
<script>
    (function () {
        var app, deps;

        deps = ['angularBootstrapNavTree'];
        deps.push('ngAnimate');

        app = angular.module('menuApp', deps);

        app.controller('menuCtrl', function ($scope, $http) {
//            var apple_selected, treedata_avm;
            $scope.my_tree_handler = function (branch) {
                $scope.menu = branch;
//                $scope.$apply();
            };
            $scope.my_data = [];
            $scope.my_tree = {};
            $scope.load = function () {

                $http.get('/menu/all_nav').success(function (data, status, headers, config) {

                    if (data.code == 0) {
                        $scope.my_data = data.data;

                        $scope.my_tree.expand_all();
                    } else {
                        $.messager.alert('', ' 失败!', 'error');
                    }

                }).error(function (data, status, headers, config) {

                });
            };

            $scope.load();

            $scope.save = function () {
                $http.post('/menu', JSON.stringify($scope.menu)).success(function (data, status, headers, config) {

                    if (data.code == 0) {
                        $.messager.alert('', ' 操作成功!', 'info');

                    } else {
                        $.messager.alert('', ' 操作失败!', 'error');
                    }

                }).error(function (data, status, headers, config) {

                });
            };

            $scope.try_adding_a_branch = function () {
                var b = $scope.my_tree.get_selected_branch();
                $scope.my_tree.add_branch(b, {
                    label: 'New Branch',
                    data: {
                        something: 42,
                        "else": 43
                    }
                });
            };
            $scope.add_root_branch = function () {
                var menu = {name: "新根菜单", status: true}
                $http.post('/menu', JSON.stringify(menu)).success(function (data, status, headers, config) {

                    if (data.code == 0) {
                        $.messager.alert('', ' 操作成功!', 'info');
                        $scope.load();
                    } else {
                        $.messager.alert('', ' 操作失败!', 'error');
                    }

                }).error(function (data, status, headers, config) {

                });

            };
        });

    }).call(this);

</script>
<#include "../layout/footer.ftl">