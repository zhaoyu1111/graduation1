layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/system/listRole', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#roleId").append("<option value=''>请选择</option>");
            $("#select").append("<option value=''>请选择</option>");
            $.each(rec.data.list, function(index, item) {
                $('#roleId').append("<option value='" + item.roleId + "'>" + item.roleName + "</option>");
                $('#select').append("<option value='" + item.roleId + "'>" + item.roleName + "</option>");
            });
        }
    }, 'json');
    form.render("select");

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/system/queryOperator',
        toolbar: '#tool_user',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'operatorName', title: '管理员名称', align: 'center'}
            ,{field:'operatorId', title: '管理员ID', align: 'center'}
            ,{field:'roleName', title: '角色名称', align: 'center'}
            ,{field:'roleId', title: '角色ID', align: 'center'}
            ,{field:'mobile', title: '手机号', align: 'center'}
            ,{field:'ctime',  title: '创建时间', align: 'center',
                templet: function (data) {
                    return createDate(data.ctime);
                }}
            ,{field:'deleted', title: '状态',align: 'center',
                templet:function (data) {
                    if(data.deleted == 0) {
                        return "正常";
                    }else {
                        return "冻结";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'roleId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_operator(data, data.operatorId);
        } else if(layEven === 'detail') {
            detail(data);
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
            content:$("#add_operator").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#operatorId").val(data.operatorId);
                    layero.find("#operatorId").attr("disabled", true);
                    layero.find("#operatorName").val(data.operatorName);
                    layero.find("#mobile").val(data.mobile);
                    $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                    $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
                    layero.find("option[value='"+data.roleId+"']").prop("selected",true);
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
                operatorRole["roleId"] = $(layero).find("select").val();
                $.post('/web/system/saveOrUpdateOperator', operatorRole, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null, null);
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
                layer.close(index);
                return false;
            }
        });
    }

    function del_operator(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/system/deleteOperator",{"operatorId" : id},function(data){
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

    function detail(data) {
        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#add_operator").html(),
            success: function (layero, index) {

            },
            yes: function (index, layero) {

            }
        });
    }

    $(document).on('click', '#add', function () {
        edit(null, "新增角色");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var operator_name = $('#accountname').val();
        var deleted = $("#status").val();
        var roleId = $("#roleId").val();
        reload(operator_name, deleted, roleId);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(operatorName, deleted, roleId) {
        tableIns.reload({
            where: {
                operatorName: operatorName,
                deleted: deleted,
                roleId: roleId
            }
        });
    }
})