layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

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

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/origin/queryClass',
        toolbar: '#class',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'classId', title: '班级编号', align: 'center'}
            ,{field:'collegeName', title: '学院名称', align: 'center'}
            ,{field:'collegeId', title: '学院Id', align: 'center'}
            ,{field:'majorName', title: '专业名称', align: 'center'}
            ,{field:'majorId', title: '专业Id', align: 'center'}
            ,{field:'headMaster', title: '班主任', align: 'center'}
            ,{field:'counselor', title: '辅导员', align: 'center'}
            ,{field:'contractor', title: '班级联系人', align: 'center'}
            ,{field:'descript', title: '班级描述', align: 'center'}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
            done: function (res, curr, couunt) {
                $('.layui-table-box').find("[data-field = 'descript']").css('display', 'none');
                $('.layui-table-box').find("[data-field = 'collegeId']").css('display', 'none');
                $('.layui-table-box').find("[data-field = 'majorId']").css('display', 'none');
            }
        ,page: true
    });

    table.on('tool(role)', function (obj) {
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
                    layero.find("#classId").val(data.classId);
                    layero.find("#classId").attr("disabled", true);
                    layero.find("#contractor").val(data.contractor);
                    layero.find("#headMaster").val(data.headMaster);
                    layero.find("#counselor").val(data.counselor);
                    layero.find("#descript").text(data.descript);
                    layero.find("#collegeName").val(data.collegeName);
                    layero.find("#majorName").val(data.majorName);
                    layero.find("option[value='"+data.collegeId+"']").prop("selected",true);
                    layero.find("option[value='"+data.majorId+"']").prop("selected",true);
                    form.render();
                }
            },
            yes: function (index, layero) {
                var classes = {};
                //var form = $("#addUserForm").serializeArray();//获取指定id的表单
                $(layero).find("input").each(function() {
                    if(this.name === "deleted"){
                        var val=$("input[checked]").val();
                        classes[this.name] = val;
                        return true;
                    }
                    classes[this.name] = this.value;
                });
                if(data != null) {
                    classes["classId"] = data.classId;
                }
                classes["descript"] = $(layero).find("#descript").val();
                //classes["collegeId"] = $(layero).find("#collegeName").val();
                //classes["majorId"] = $(layero).find("#majorName").val();
                $.post('/web/origin/saveOrUpdateClass', classes, function (rec) {//得到数据提交到后端进行更新
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

    function delClass(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/system/deleteRole",{"roleId" : id},function(data){
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

    $(document).on('click', '#add', function () {
        edit(null, "新增角色");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var className = $('#className').val();
        var collegeId = $('#search_collegeName').val();
        var majorId = $("#search_majorName").val();
        reload(className, collegeId, majorId);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(className, collegeId, majorId) {
        tableIns.reload({
            where: {
                className: className,
                collegeId: collegeId,
                majorId: majorId
            }
        });
    }
});