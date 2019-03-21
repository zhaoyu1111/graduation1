layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/article/queryArticle',
        toolbar: '#tool_article',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'articleId', title: 'ID'}
            ,{field:'title', title: '文章标题', align: 'center'}
            ,{field:'author', title: '作者', align: 'center'}
            ,{field:'menuId', title: '二级菜单', align: 'center'}
            ,{field:'source', title: '文章来源', align: 'center'}
            ,{field:'count', title: '点击数', align: 'center'}
            ,{field:'status', title: '状态', align: 'center',
                templet:function (data) {
                    if(data.status == 1) {
                        return "上线";
                    }else if(data.status == 2){
                        return "下线";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo', width: 185}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'articleId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_article(data, data.articleId);
        } else if(layEven === 'detail') {
            detail(data.articleId);
        } else if(layEven === 'down') {
            down(data.articleId);
        }
    });

    function edit(data, title){

        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area: ['30%'],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#edit_article").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#title").val(data.title);
                    layero.find("#author").val(data.author);
                    layero.find("#menuId").val(data.menuId);
                    layero.find("#source").val(data.source);
                    form.render();
                }
            },
            yes: function (index, layero) {
                var article = {};
                $(layero).find("input").each(function() {
                    article[this.name] = this.value;
                });
                if(data != null) {
                    article["articleId"] = data.articleId;
                }
                $.post('/web/article/saveOrUpdateArticle', article, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null, null);
                        layer.close(index);
                        return false;
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
            }
        });
    }

    function del_article(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/article/deleteArticle",{"articleId" : id},function(data){
                    if (data.code == "2000") {
                        layer.alert(data.message,{icon: 6}, function(){
                            layer.closeAll();
                            reload(null, null, null);
                        });
                    } else {
                        layer.alert(data.message);
                    }
                });
            }, function(){
                layer.closeAll();
            });
        }
    }

    function down(id) {
        if(null!=id){
            layer.confirm('您确定要执行该操作？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/article/toggleStatus",{"articleId" : id},function(data){
                    if (data.code == "2000") {
                        layer.alert(data.message,{icon: 6}, function(){
                            layer.closeAll();
                            reload(null, null, null);
                        });
                    } else {
                        layer.alert(data.message);
                    }
                });
            }, function(){
                layer.closeAll();
            });
        }
    }

    function detail(id) {
        layer.open({
            type: 1,
            title: "详情",
            skin: "myclass",
            area: ["30%"],
            content: $("#detail").html(),
            success: function (layero, index) {
                $.post("/web/article/getContext", {"articleId": id}, function (res) {
                    if (res.code === '2000') {
                        $("#detail").append(res.data);
                    }
                    form.render();
                });
            }
        });
    }

    $(document).on('click', '#add', function () {
        edit(null, "新增角色");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var roleName = $('#unit_name').val();
        var property = $("#search_property").val();
        var status = $("#search_status").val();
        reload(roleName, property, status);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(unitName, property, status) {
        tableIns.reload({
            where: {
                unitName: unitName,
                property: property,
                status: status
            }
        });
    }
})