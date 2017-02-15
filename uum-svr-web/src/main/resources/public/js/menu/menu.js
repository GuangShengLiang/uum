/**
 * Created by user on 2016/11/22.
 */
var app = angular.module("menuApp", []);
app.controller("menuCtrl", function ($scope, $http) {
    $scope.menuList = [{}];

    $scope.loadMenu = function () {
        $http.get('/menu/account/1').success(function (data, status, headers, config) {

            $scope.menuList = data;

        }).error(function (data, status, headers, config) {
        });
    };
    $scope.openTab = function (menu) {
        if ($('#content').tabs('exists', menu.name)) {
            $('#content').tabs('select', menu.name);
        } else {
            var content = '<iframe scrolling="auto" frameborder="0"  src="' + menu.tpl + '" style="width:100%;height:100%;"></iframe>';
            $('#content').tabs('add', {
                title: menu.name,
                content: content,
                closable: true
            });
        }
    };
});