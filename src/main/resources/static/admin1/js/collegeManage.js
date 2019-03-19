layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/origin/queryCollege',
        toolbar: '#college',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'collegeId', title: '学院编号', align: 'center'}
            ,{field:'collegeName', title: '学院名称', align: 'center'}
            ,{field:'operatorName', title: '学院联系人', align: 'center'}
            ,{field:'mobile', title: '联系方式', align: 'center'}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]]
        ,page: true
    });

    table.on('tool(college)', function (obj) {
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
            content:$("#addCollege").html(),
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
                var college = {};
                //var form = $("#addUserForm").serializeArray();//获取指定id的表单
                $(layero).find("input").each(function() {
                    college[this.name] = this.value;
                });
                if(data != null) {
                    college["college"] = data.roleId;
                }
                $.post('/web/origin/saveOrUpdateCollege', college, function (rec) {//得到数据提交到后端进行更新
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