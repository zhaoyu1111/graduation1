layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/origin/getCollege', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $.each(rec.data, function(index, item) {
                $('#collegeName').append("<option value='" + item.collegeId + "'>" + item.collegeName + "</option>");
            });
        }
    }, 'json');
    form.render();

    form.on('select(collegeName)', function(data){         //级联操作
        $('#majorName').empty();
        //$.ajaxSettings.async = false;
        $.post('/web/origin/getMajor', {"collegeId" : data.value}, function (rec) {//得到数据提交到后端进行更新
            if (rec.code === "2000") {
                $.each(rec.data, function(index, item) {
                    $('#majorName').append("<option value='" + item.majorId + "'>" + item.majorName + "</option>");
                });
            }
            form.render('select', 'majorName');
        }, 'json');

    });

    form.on('select(majorName)', function(data){         //级联操作
        $('#className').empty();
        $.ajaxSettings.async = false;
        $.post('/web/origin/getClass', {"majorId" : data.value}, function (rec) {//得到数据提交到后端进行更新
            if (rec.code === "2000") {
                $.each(rec.data, function(index, item) {
                    $('#className').append("<option value='" + item.majorId + "'>" + item.majorName + "</option>");
                });
            }
        }, 'json');
        form.render('select');
    });

    var tableIns = table.render({
        elem: '#mytable',
        url: '/api/user/queryUser',
        toolbar: '#tool_user',
        height: 525,
        cols: [[
            {field:'studentId', title: '学号', align: "center", width: 63}
            ,{field:'userName', title: '用户名称', align: "center", width: 72}
            ,{field:'gender', title: '性别', align: "center", width: 57,
                templet: function(data) {
                    if(data.gender == 0) {
                        return "男";
                    }else {
                        return "女";
                    }
                }}
            ,{field:'mobile', title: '手机号', align: "center", width: 89}
            ,{field:'email', title: '邮箱', align: "center", width: 135}
            ,{field:'collegeId', title: '学院ID', align: "center"}
            ,{field:'collegeName', title: '学院名称', align: "center"}
            ,{field:'majorId', title: '专业ID', align: "center"}
            ,{field:'majorName', title: '专业名称', align: "center"}
            ,{field:'classId', title: '班级ID', align: "center"}
            ,{field:'className', title: '班级名称', align: "center", width: 67}
            ,{field:'deleted', title: '状态', align: "center",
                templet:function (data) {
                    if(data.deleted == 0) {
                        return "正常";
                    }else {
                        return "冻结";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo', width: 128}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'majorId']").css('display', 'none');
            $('.layui-table-box').find("[data-field = 'collegeId']").css('display', 'none');
            $('.layui-table-box').find("[data-field = 'classId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(user)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_user(data, data.unitId);
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
            area: ['630px', '500px'],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#add_user").html(),
            success: function (layero, index) {
                if(data != null) {
                    $.post('/web/user/getUser', {"studentId": data.studentId}, function (res) {
                        if(res.code === '2000') {

                            form.render();
                        } else {
                            layer.msg(res.message);
                        }
                    });
                    form.render();
                }else {
                    $("input:radio").removeAttr("checked");
                    form.render();
                }
            },
            yes: function (index, layero) {
                var recruitUnit = {};
                $(layero).find("input").each(function() {
                    recruitUnit[this.name] = this.value;
                });
                recruitUnit["deleted"] = $("input[name='deleted']:checked").val();
                recruitUnit["scale"] = $(layero).find("#scale").val();
                recruitUnit["property"] = $(layero).find("#property").val();
                recruitUnit["direct"] = $(layero).find("#direct").val();
                if(data != null) {
                    recruitUnit["unitId"] = data.unitId;
                }
                $.post('/web/recruit/saveOrUpdateUnit', recruitUnit, function (rec) {//得到数据提交到后端进行更新
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

    function del_user(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/user/deleteUser",{"studentId" : id},function(data){
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