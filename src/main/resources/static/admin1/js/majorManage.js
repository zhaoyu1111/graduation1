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
    form.render();

    /*form.on('select(major)', function(data){         //级联操作

    });
    form.render();*/


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
            delCollege(data, data.roleId);
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
                    layero.find("#collegeId").val(data.collegeId);
                    layero.find("#collegeId").attr("disabled", true);
                    layero.find("#collegeName").val(data.collegeName);
                    layero.find("#operatorName").val(data.operatorName);
                    form.render();
                }else {
                    $("input:radio").removeAttr("checked");
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

    function delCollege(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/origin/deleteCollege",{"collegeId" : id},function(data){
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
        var collegeId = $('#serach_college_id').val();
        var collegeName = $("#serach_college_name").val();
        reload(collegeId, collegeName);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null);
    });

    function reload(collegeId, collegeName) {
        tableIns.reload({
            where: {
                collegeId: collegeId,
                collegeName: collegeName
            }
        });
    }
});