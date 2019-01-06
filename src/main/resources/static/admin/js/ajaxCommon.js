$(function () {
    $.ajaxSetup({
        headers: {
            "token": sessionStorage.getItem("token")
        },
        success: function (data) {
            if(data.code == 2001) {
                layer.alert(data.message);
            }else if(data.code == 2003) {
                layui.msg(data.message);
            }else {
                layer.msg(1);
            }
        }
    });
})