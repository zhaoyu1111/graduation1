layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;


    $(document).on('click', '#submit', function () {
        var article = {};
        article["context"] = UE.getEditor('editor').getContent();
        article["menuId"] = $("#child_menu").val();
        article["title"] = $("#title").val();
        article["author"] = $("#author").val();
        article["source"] = $("#source").val();

        $.post('/web/article/saveOrUpdateArticle', article, function(res) {
            if(res.code == '2000') {
                layer.msg('发布成功',{icon:1,time:1500, end:function () {
                        window.location.reload();
                    }});
            }else {
                layer.msg(res.message,{icon:1,time:1500});
            }
        }, 'json');
    });
});