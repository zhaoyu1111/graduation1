layui.use(['layer', 'form', 'jquery'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    $("#submit").click(function () {
        var oldPwd = $("#oldPwd").val();
        var newPwd = $("#newPwd").val();
        var confirmPwd = $("#confirmPwd").val();
        $.ajax({
            url: '/web/system/changePwd',
            type: 'post',
            dataType: 'json',
            headers: {
                token: sessionStorage.getItem('token')
            },
            data: {'oldPwd': oldPwd, 'newPwd': newPwd, 'confirmPwd': confirmPwd},
            success: function (data) {
                if(data.code == '2000') {
                    alert("修改成功");
                }else {
                    alert(data.message);
                }
            }
        });
    });
});

