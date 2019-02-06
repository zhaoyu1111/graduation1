layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/system/listRole', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#select").append("<option value=''>请选择</option>");
            $.each(rec.data.list, function(index, item) {
                $('#select').append("<option value='" + item.roleId + "'>" + item.roleName + "</option>");
            });
        } else {
            /*$("#select").append(new Option("暂无数据", ""));*/
        }
    }, 'json');
    form.render("select");

    table.render({
        elem: '#mytable',
        url: '',
        toolbar: '#toolUser',
        height: 460,
        cols: [[
            {field:'studentId', title: '学号'}
            ,{field:'userName', title: '用户名称'}
            ,{field:'gender', title: '性别'}
            ,{field:'mobile', title: '手机号'}
            ,{field:'email', title: '邮箱'}
            ,{field:'classId', title: '班级ID'}
            ,{field:'className', title: '班级名称'}
            ,{field:'collegeId', title: '学院ID'}
            ,{field:'collegeName', title: '学院名称'}
            ,{field:'ctime',  title: '创建时间',
                templet: function (data) {
                    return createTime(data.ctime);
                }}
            ,{field:'deleted', title: '状态',
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
            edit(data, '查看详情');
        } else if(layEven === 'del') {
            delRole(data, data.roleId);
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
})