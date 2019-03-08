layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var status = [{
        "title": "待审核",
        "value": 1
    }, {
        "title": "正在招聘",
        "value": 2
    }, {
        "title": "已结束",
        "value": 3
    }];

    $.post('/web/recruit/getAllUnit', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $.each(rec.data, function(index, item) {
                $('#unitId').append("<option value='" + item.unitId + "'>" + item.unitName + "</option>");
            });
        }
    }, 'json');
    form.render();

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/recruit/queryRecruit',
        toolbar: '#tool_recruit',
        height: 460,
        cols: [[
            {type:'checkbox'}
            ,{field:'recuritId', title: 'ID'}
            ,{field:'title', title: '单位名称'}
            ,{field:'salary', title: '薪资'}
            ,{field:'members', title: '招聘人数'}
            ,{field:'resumes', title: '简历投递数'}
            ,{field:'endTime', title: '结束时间'}
            ,{field:'contractor', title: '联系人'}
            ,{field:'posName', title: '职位名称'}
            ,{field:'status', title: '招聘状态',
                templet: function (data) {
                    var status_data;
                    $.each(status, function (index, item) {
                        if(item.value === data.status) {
                            status_data = item.title;
                        }
                    });
                    return status_data;
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'recuritId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(recruit)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            deleted(data, data.recruitId);
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
            content:$("#add_recruit").html(),
            success: function (layero, index) {
                if(data != null) {
                    $.post('/web/recruit/getRecruit', {"recuritId": data.recuritId}, function (res) {
                        if(res.code === '2000') {
                            layero.find("#title").val(res.data.title);
                            //layero.find("#unitName").attr("disabled", true);
                            layero.find("#salary").val(res.data.salary);
                            layero.find("option[value='"+data.unitId+"']").prop("selected",true);
                            layero.find("#members").val(res.data.members);
                            layero.find("#endTime").val(res.data.endTime);
                            layero.find("#contractor").val(res.data.contractor);
                            layero.find("#mobile").val(res.data.mobile);
                            layero.find("#posName").val(res.data.posName);
                            layero.find("#welfare").val(res.data.welfare);
                            layero.find("#workPlace").val(res.data.workPlace);
                            layero.find("#email").val(res.data.email);
                            layero.find("#posDescript").text(res.data.posDescript);
                            $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                            $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);

                            $("input[name=status][value='1']").attr("checked", data.status == 1 ? true : false);
                            $("input[name=status][value='2']").attr("checked", data.status == 2 ? true : false);
                            $("input[name=status][value='3']").attr("checked", data.status == 3 ? true : false);
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
            yes:  function (index, layero) {
                var recruit = {};
                $(layero).find("input").each(function() {
                    recruit[this.name] = this.value;
                });
                recruit["deleted"] = $("input[name='deleted']:checked").val();
                recruit["unitId"] = $(layero).find("#unitId").val();
                recruit["posDescript"] = $(layero).find("#posDescript").val();
                if(data != null) {
                    recruit["recuritId"] = data.recuritId;
                }
                $.post('/web/recruit/saveOrUpdateRecruit', recruit, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null, null);
                        layer.close(index);
                        return false;
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
                /*layer.close(index);
                return false;*/
            }
        });
    }

    function deleted(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/recruit/deleteRecruit",{"recruitId" : id},function(data){
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