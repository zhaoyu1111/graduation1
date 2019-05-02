layui.use(['layer', 'table', 'form', 'jquery', 'upload'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form,
        upload = layui.upload;

    $.post('/web/origin/getCollege', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#search_collegeName").append("<option value=''>请选择</option>");
            $.each(rec.data, function(index, item) {
                $('#search_collegeName').append("<option value='" + item.collegeId + "'>" + item.collegeName + "</option>");
            });
        }
    }, 'json');
    form.render();

    form.on('select(search_collegeName)', function(data){         //级联操作
        $.ajaxSettings.async = false;
        $.post('/web/origin/getMajor', {"collegeId" : data.value}, function (rec) {//得到数据提交到后端进行更新
            if (rec.code === "2000") {
                $("#search_majorName").append("<option value=''>请选择</option>");
                $.each(rec.data, function(index, item) {
                    $('#search_majorName').append("<option value='" + item.majorId + "'>" + item.majorName + "</option>");
                });
            }
        }, 'json');
        form.render('select');
    });

    /*var uploadInst = upload.render({
        elem: '#excel',
        url: '/api/user/excelImport',
        done: function (res) {
            alert(res.code);
        }
    });*/

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
            ,{field:'status', title: '状态', align: "center",
                templet:function (data) {
                    if(data.status == 0) {
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
                    $.post('/api/user/getUser', {"studentId": data.studentId}, function (res) {
                        if(res.code === '2000') {
                            layero.find("#studentId").val(res.data.studentId);
                            layero.find("#studentId").attr("disabled", true);
                            layero.find("#userName").val(res.data.userName);
                            layero.find("#collegeName").val(res.data.collegeName);
                            layero.find("#majorName").val(res.data.majorName);
                            layero.find("#className").val(res.data.className);
                            layero.find("#mobile").val(res.data.mobile);
                            layero.find("#birthday").val(res.data.birthday);
                            layero.find("#email").val(res.data.email);
                            layero.find("#currentCity").val(res.data.currentCity);
                            layero.find("#homeAddress").val(res.data.homeAddress);
                            layero.find("#qq").val(res.data.qq);
                            layero.find("#wechat").val(res.data.wechat);
                            layero.find("#introduce").text(res.data.introduce);
                            $("input[name=gender][value='0']").attr("checked", data.gender == 0 ? true : false);
                            $("input[name=gender][value='1']").attr("checked", data.gender == 1 ? true : false);

                            $("input[name=status][value='0']").attr("checked", data.status == 0 ? true : false);
                            $("input[name=status][value='1']").attr("checked", data.status == 1 ? true : false);
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
                var user = {};
                $(layero).find("input").each(function() {
                    user[this.name] = this.value;
                });
                user["status"] = $("input[name='status']:checked").val();
                user["gender"] = $("input[name='gender']:checked").val();
                user["introduce"] = $(layero).find("#introduce").val();
                if(data != null) {
                    user["studentId"] = data.studentId;
                }
                $.post('/api/user/saveOrUpdateUser', user, function (rec) {//得到数据提交到后端进行更新
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

    /*$(document).on('click', '#excel', function () {
        upload.render({
            elem: '#excel',
            url: '/api/user/excelImport',
            accept: 'file',
            multiple: true,
            before: function(){
                alert(2);
            },
            done: function (res) {
                alert(res.code);
            },
            error: function(){
                //演示失败状态，并实现重传
                /!*var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });*!/
            }
        });
    });*/

    $(document).on('click', '#search',  function () {
        var userName = $('#user_name').val();
        var collegeName = $("#search_collegeName").val();
        var majorName = $("#search_majorName").val();
        reload(userName, collegeName, majorName);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(userName, collegeName, majorName) {
        tableIns.reload({
            where: {
                userName: userName,
                collegeName: collegeName,
                majorName: majorName
            }
        });
    }
})