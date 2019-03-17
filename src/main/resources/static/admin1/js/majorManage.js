layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $.post('/web/origin/getCollege', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#collegeName").append("<option value=''>请选择</option>");
            $("#search_collegeName").append("<option value=''>请选择</option>");
            $.each(rec.data, function(index, item) {
                $('#collegeName').append("<option value='" + item.collegeId + "'>" + item.collegeName + "</option>");
                $('#search_collegeName').append("<option value='" + item.collegeId + "'>" + item.collegeName + "</option>");
            });
        }
    }, 'json');
    form.render();

    form.on('select(collegeName)', function(data){         //级联操作
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
        url: '/web/origin/queryMajor',
        toolbar: '#major',
        height: 460,
        cols: [[
            {type:'checkbox'}
            ,{field:'majorId', title: '专业编号', sort: true}
            ,{field:'majorName', title: '专业名称'}
            ,{field:'collegeName', title: '学院名称'}
            ,{field:'collegeId', title: '学院编号'}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]]
        ,page: true
    });

    table.on('tool(major)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            delMajor(data, data.majorId);
        }
    });

    function edit(data, title){

        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#addMajor").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#majorId").val(data.majorId);
                    layero.find("#majorId").attr("disabled", true);
                    layero.find("#majorName").val(data.majorName);
                    layero.find("option[value='"+data.collegeId+"']").prop("selected",true);
                    form.render();
                }
            },
            yes: function (index, layero) {
                var major = {};
                $(layero).find("input").each(function() {
                    major[this.name] = this.value;
                });
                major["collegeId"] = $(layero).find("#collegeName").val();
                $.post('/web/origin/saveOrUpdateMajor', major, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null);
                        layer.close(index);
                        return false;
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
            }
        });
    }

    function delMajor(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/origin/deleteMajor",{"majorId" : id},function(data){
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
        var collegeId = $('#search_collegeName').val();
        var majorId = $("#search_majorName").val();
        reload(collegeId, majorId);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null);
    });

    function reload(collegeId, majorId) {
        tableIns.reload({
            where: {
                collegeId: collegeId,
                majorId: majorId
            }
        });
    }
});