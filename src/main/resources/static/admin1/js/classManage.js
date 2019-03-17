layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/origin/getCollege', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#collegeName").append("<option value=''>请选择</option>");
            $.each(rec.data, function(index, item) {
                $('#collegeName').append("<option value='" + item.collegeId + "'>" + item.collegeName + "</option>");
            });
        }
    }, 'json');
    //form.render();

    form.on('select(collegeName)', function(data){         //级联操作
        $("#majorName").empty();
        $.ajaxSettings.async = false;
        $.post('/web/origin/getMajor', {"collegeId" : data.value}, function (rec) {//得到数据提交到后端进行更新
            if (rec.code === "2000") {
                $("#majorName").append("<option value=''>请选择</option>");
                $.each(rec.data, function(index, item) {
                    $('#majorName').append("<option value='" + item.majorId + "'>" + item.majorName + "</option>");
                });
            }
        }, 'json');
        form.render('select');
    });


    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/origin/queryClass',
        toolbar: '#class',
        height: 460,
        cols: [[
            {type:'checkbox'}
            ,{field:'classId', title: '班级编号', sort: true}
            ,{field:'collegeName', title: '学院名称'}
            ,{field:'majorName', title: '专业名称'}
            ,{field:'headMaster', title: '班主任'}
            ,{field:'counselor', title: '辅导员'}
            ,{field:'contractor', title: '班级联系人'}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]]
        ,page: true
    });

    table.on('tool(class)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            delClass(data, data.roleId);
        }
    });

    function edit(data, title){

        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#addClass").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#roleName").val(data.roleName);
                    $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                    $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
                    form.render();
                }else {

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

    function delClass(obj,id) {
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
});