layui.use(['layer', 'table', 'form', 'jquery', 'laydate'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form,
        laydate1 = layui.laydate;
    laydate2 = layui.laydate;

    laydate1.render({
        elem: '#search_endtime',
        zIndex: 99999
    });

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

    var salary = [];

    $.post('/web/recruit/getAllUnit', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $.each(rec.data, function(index, item) {
                $('#unit_name').append("<option value='" + item.unitId + "'>" + item.unitName + "</option>");
                $('#unitId').append("<option value='" + item.unitId + "'>" + item.unitName + "</option>");
            });
        }
    }, 'json');
    form.render();

    $.post('/web/system/unitData', {'dictValue': 'sl'}, function (rec) {
        salary = rec.data;
        if (rec.code === "2000") {
            $.each(rec.data, function(index, item) {
                $('#salary').append("<option value='" + item.dictdataValue + "'>" + item.dictdataName + "</option>");
            });
        }
    }, 'json');
    form.render();

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/recruit/queryRecruit',
        toolbar: '#tool_recruit',
        height: 525,
        cols: [[
            {field:'recuritId', title: 'ID'}
            ,{field:'title', title: '单位名称', align: 'center', width: 85}
            ,{field:'salary', title: '薪资', align: 'center', width: 85,
                templet: function (data) {
                    var salary_data;
                    $.each(salary, function (index, item) {
                        if(item.dictdataValue === data.salary) {
                            salary_data = item.dictdataName;
                        }
                    });
                    return salary_data;
                }}
            ,{field:'members', title: '招聘人数', align: 'center', width: 70}
            ,{field:'resumes', title: '简历投递数', align: 'center',width: 75,
                templet:function(data){
                    if(data.resumes == null) {
                        return '0';
                    }else {
                        return data.resumes;
                    }
                }}
            ,{field:'endTime', title: '结束时间', align: 'center',width: 100}
            ,{field:'contractor', title: '联系人', align: 'center', width: 75}
            ,{field:'posName', title: '职位名称', align: 'center', width: 100}
            ,{field:'status', title: '招聘状态', align: 'center', width: 80,
                templet: function (data) {
                    var status_data;
                    $.each(status, function (index, item) {
                        if(item.value === data.status) {
                            status_data = item.title;
                        }
                    });
                    return status_data;
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo', width: 135}
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
            content:$("#add_recruit"),
            success: function (layero, index) {
                if(data != null) {
                    $.post('/web/recruit/getRecruit', {"recuritId": data.recuritId}, function (res) {
                        if(res.code === '2000') {
                            layero.find("#title").val(res.data.title);
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
                            layero.find("#qq").text(res.data.qq);
                            layero.find("#wechat").text(res.data.wechat);
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
                recruit["status"] = $("input[name='status']:checked").val();
                //recruit["status"] = $(layero).find("input[name='status']").val();
                recruit["unitId"] = $(layero).find("#unitId").val();
                recruit["salary"] = $(layero).find("#salary").val();
                recruit["posDescript"] = $(layero).find("#posDescript").val();
                recruit["deleted"] = $("input[name='deleted']:checked").val();
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
        var unitId = $('#unit_name').val();
        var endTime = $("#search_endtime").val();
        var status = $("#search_status").val();
        reload(unitId, endTime, status);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(unitId, endTime, status) {
        tableIns.reload({
            where: {
                unitId: unitId,
                endTime: endTime,
                status: status
            }
        });
    }
});