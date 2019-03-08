layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/system/getParentMenu', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("select[name=parent-menu]").append("<option value=''>请选择</option>");
            $("#select").append("<option value='0'>根目录</option>");
            $.each(rec.data, function(index, item) {
                $('select[name=parent-menu]').append("<option value='" + item.menuId + "'>" + item.title + "</option>");
            });
        }
    }, 'json');
    form.render("select");

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/system/queryMenu',
        toolbar: '#tool_menu',
        height: 460,
        cols: [[
            {type:'checkbox'}
            ,{field:'menuId', title: '菜单ID'}
            ,{field:'title', title: '菜单标题'}
            ,{field:'href', title: '菜单url'}
            ,{field:'icon', title: 'icon'}
            ,{field:'parentId', title: '父菜单'}
            ,{field:'ctime',  title: '创建时间',
                templet: function (data) {
                    return createDate(data.ctime);
                }}
            ,{field:'status', title: '状态',
                templet:function (data) {
                    if(data.status == 0) {
                        return "正常";
                    }else {
                        return "冻结";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        page: true
    });

    table.on('tool(activity)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_menu(data, data.menuId);
        }
    });

    form.on('radio', function (data) {
        $("input:radio").removeAttr("checked");
        $(data.elem).attr('checked', 'checked');
    });

    function edit(data, title){

        form.render('select');
        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#add_menu").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#title").val(data.title);
                    layero.find("#operatorId").attr("disabled", true);
                    layero.find("#href").val(data.href);
                    layero.find("#icon").val(data.icon);
                    $("input[name=deleted][value='0']").attr("checked", data.status == 0 ? true : false);
                    $("input[name=deleted][value='1']").attr("checked", data.status == 1 ? true : false);
                    layero.find("option[value='"+data.parentId+"']").prop("selected",true);
                    if(data.parentId == 0) {
                        layero.find("select").prop("disabled", true);
                    }/*else {
                        layero.find("option[value='"+data.parentId+"']").prop("selected",true);
                    }*/
                    form.render();
                }else {
                    $("input:radio").removeAttr("checked");

                    form.render();
                }
            },
            yes: function (index, layero) {
                var operatorRole = {};
                $(layero).find("input").each(function() {
                    if(this.name === "deleted"){
                        var val=$("input[checked]").val();
                        operatorRole[this.name] = val;
                        return true;
                    }
                    operatorRole[this.name] = this.value;
                });
                operatorRole["parentId"] = $(layero).find("select").val();
                if(data != null) {
                    operatorRole['menuId'] = data.menuId;
                }
                $.post('/web/system/saveOrUpdateMenu', operatorRole, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null);
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
                layer.close(index);
                return false;
            }
        });
    }

    function del_menu(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/system/deleteMenu",{"menuId" : id},function(data){
                    if (data.code == "2000") {
                        layer.alert(data.message,{icon: 6}, function(){
                            layer.closeAll();
                            reload(null, null);
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

    $(document).on('click', '#add', function () {
        edit(null, "新增角色");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var menu_title = $('#menu_title').val();
        var parent_id = $("#parentId").val();
        reload(menu_title, parent_id);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null);
    });

    function reload(menu_title, parent_id) {
        tableIns.reload({
            where: {
                title: menu_title,
                parentId: parent_id
            }
        });
    }
})