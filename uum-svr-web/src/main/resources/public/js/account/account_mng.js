/**
 * Created by user on 2016/11/14.
 */
function doSearch(queryParam) {

    $('#dg').datagrid({
        method: "GET",
        url: '/account',
        queryParams: queryParam,
        scrollbarSize: 50,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        pageList: [20, 50, 100, 200],
       /* columns: [[
            {field: 'id', title: 'UID', width: 100},
            {field: 'name', title: '名称', width: 100},
            {field: 'statusName', title: '状态', width: 100},
            {field: 'ctimeStr', title: '创建时间', width: 100}
        ]],*/
        onLoadSuccess: function (data) {
            if (data.code != 0) {
                $.messager.alert('', '查询失败!', 'error');
            }
        },
        onLoadError: function () {
            $.messager.alert('', '查询失败!', 'error');
        }
    });
}

var app = angular.module("accountApp", []);
app.controller("accountCtrl", function ($scope, $http) {
    $scope.queryParam = {};

    $scope.search = function () {
        doSearch($scope.queryParam);
    };
});