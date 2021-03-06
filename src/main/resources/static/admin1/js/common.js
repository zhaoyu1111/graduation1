function createTime(v) {
    var date = new Date(v);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ("0" + d) : d;
    var h = date.getHours();
    h = h < 10 ? ("0" + h) : h;
    var M = date.getMinutes();
    M = M < 10 ? ("0" + M) : M;
    var str = y + "-" + m + "-" + d + " " + h + ":" + M;
    return str;
}

function createDate(v) {
    var date = new Date(v);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ("0" + d) : d;
    var str = y + "-" + m + "-" + d;
    return str;
}

$(function () {
    $.ajaxSetup({
        type: 'post',
        headers: {
            token: sessionStorage.getItem('token')
        },
        complete: function (XMLHttpRequest, status) {
            var code = XMLHttpRequest.responseText;
            var jsonData = JSON.parse(code);
            if(jsonData.code == '2001') {
                window.location = 'login.html';
            }
        }
    })
})