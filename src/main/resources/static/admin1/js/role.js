layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var menu;

        var tableIns = table.render({
            elem: '#mytable',
            url: '/web/system/listRole',
            toolbar: '#operator',
            height: 525,
            response: {
                statusName: 'code'
                , statusCode: '2000'
                , msgName: 'message'
                , dataName: 'data'
                , countName: 'total'
            },
            cols: [[
                {type:'checkbox'}
                ,{field:'roleId', title: 'ID', align: 'center'}
                ,{field:'roleName', title: '角色名称', align: 'center'}
                ,{field:'ctime',  title: '创建时间', align: 'center',
                    templet: function (data) {
                        return createTime(data.ctime);
                    }}
                ,{field:'remark', title: '备注', align: 'center'}
                ,{field:'deleted', title: '状态', align: 'center',
                    templet:function (data) {
                        if(data.deleted == 0) {
                            return "正常";
                        }else {
                            return "冻结";
                        }
                    }}
                ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
            ]]
        });

        table.on('tool(role)', function (obj) {
            var data = obj.data;
            var layEven = obj.event;

            if(layEven === 'edit') {
                edit(data, '编辑');
            } else if(layEven === 'del') {
                delRole(data, data.roleId);
            } else if(layEven === 'resource_allocation') {
                resource_allocation('权限分配', data.roleId);
            }
        });

        form.on('radio', function (data) {
            $("input:radio").removeAttr("checked");
            $(data.elem).attr('checked', 'checked');
        });

        function edit(data, title){

            layer.open({
                type:1,
                title:title,
                skin:"myclass",
                area:["30%"],
                btn: ['确认', '取消'],//弹出层按钮
                content:$("#addrole").html(),
                success: function (layero, index) {
                    if(data != null) {
                        layero.find("#roleName").val(data.roleName);
                        $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                        $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
                        form.render();
                    }else {
                        $("input:radio").removeAttr("checked");
                        form.render();
                    }
                },
                yes: function (index, layero) {
                    var role = {};
                    //var form = $("#addUserForm").serializeArray();//获取指定id的表单
                    $(layero).find("input").each(function() {
                        if(this.name === "deleted"){
                            var val=$("input[checked]").val();
                            role[this.name] = val;
                            return true;
                        }
                        role[this.name] = this.value;
                    });
                    if(data != null) {
                        role["roleId"] = data.roleId;
                    }
                    $.post('/web/system/saveOrUpdateRole', role, function (rec) {//得到数据提交到后端进行更新
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

    function delRole(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/system/deleteRole",{"roleId" : id},function(data){
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

    form.on('checkbox', function (data) {
        //$("input:checkbox").removeAttr("checked");
        $(data.elem).attr('checked', true);
    });

    function resource_allocation(title, id) {
        var menu;
        $.ajaxSettings.async = false;
        $.post('/web/system/getAllMenu', {roleId: id}, function (res) {
            if(res.code === '2000') {
                menu = res.data;
            }
        });
        var xtree1 = new layuiXtree({
            elem: 'xtree1'   //(必填) 放置xtree的容器，样式参照 .xtree_contianer
            , form: form     //(必填) layui 的 from
            , data: menu     //(必填) json数据
        });
        layer.open({
            type: 1,
            title: title,
            skin: "myclass",
            area: ["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content: $("#add_resource").html(),
            success: function (layero, index) {
                xtree1.render();
                $.post('/web/system/getResourceByRoleId', {roleId: id}, function (rec) {
                    if(rec.code === '2000') {
                        var data = rec.data;
                        for(var i = 0; i < data.length; i++) {
                            $("input:checkbox[value='" + data[i].menuId + "']").attr('checked', true);
                        }
                    }
                });
                form.render();
            },
            yes: function (layero, index) {

                var check = $(".layui-xtree-checkbox");
                var arr = new Array();
                var ind = 0;
                for(var i = check.length / 2; i < check.length; i++) {
                    if(check[i].checked) {
                        arr[ind] = check[i].value;
                        ind++;
                    }
                }
                $.post('/web/system/editResource', {roleId: id, resources: arr }, function (res) {
                    layer.msg(res.message);
                });
                layer.closeAll();
            }
        });
    }

    $(document).on('click', '#add', function () {
        edit(null, "新增角色");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var roleName = $('#accountname').val();
        var deleted = $("#status").val();
        reload(roleName, deleted);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null);
    });

    function reload(roleName, deleted) {
        tableIns.reload({
            where: {
                roleName: roleName,
                deleted: deleted
            }
        });
    }
});