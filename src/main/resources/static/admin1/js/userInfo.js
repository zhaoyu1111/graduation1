layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    $.post('/api/user/getUserInfo', null, function (data) {
        $("#userName").val(data.userName);
        $("#role").val(data.role);
        $("#realName").val(data.userName);
        $("#gender").val(data.gender);
    });

});