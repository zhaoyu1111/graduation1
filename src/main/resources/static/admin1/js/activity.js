layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/activity/queryActivity',
        toolbar: '#tool_activity',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'activityId', title: '活动ID', align: 'center'}
            ,{field:'activityName', title: '活动名称', align: 'center'}
            ,{field:'activityAddr', title: '活动地址', align: 'center'}
            ,{field:'startTime', title: '开始时间', align: 'center'}
            ,{field:'endTime', title: '结束时间', align: 'center'}
            ,{field:'activityNumber', title: '活动人数', align: 'center'}
            ,{field:'signNumber', title: '报名人数', align: 'center'}
            ,{field:'interests', title: '感兴趣数', align: 'center'}
            ,{field:'status', title: '当前状态', align: 'center',
                templet:function (data) {
                    if(data.status == 1) {
                        return "待审核";
                    }else if(data.status == 2){
                        return "正在进行";
                    } else {
                        return "已结束";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'activityId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            deleted(data, data.activityId);
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
            area: ['630px', '430px'],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#add_activity").html(),
            success: function (layero, index) {
                if(data != null) {
                    $.post('/web/activity/getActivity', {"activityId": data.activityId}, function (res) {
                        if(res.code === '2000') {
                            layero.find("#activityName").val(res.data.activityName);
                            layero.find("#activityAddr").val(res.data.activityAddr);
                            layero.find("#activityNumber").val(res.data.activityNumber);
                            layero.find("#startTime").val(res.data.startTime);
                            layero.find("#endTime").val(res.data.endTime);
                            layero.find("#leaderName").val(res.data.leaderName);
                            layero.find("#leaderMobile").val(res.data.leaderMobile);
                            layero.find("#activityDesc").text(res.data.activityDesc);
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
            yes: function (index, layero) {
                var activity = {};
                $(layero).find("input").each(function() {
                    activity[this.name] = this.value;
                });
                activity["activityDesc"] = $(layero).find("#activityDesc").val();
                activity["status"] = $("input[name='status']:checked").val();

                if(data != null) {
                    activity["activityId"] = data.activityId;
                }
                $.post('/web/activity/saveOrUpdateActivity', activity, function (rec) {//得到数据提交到后端进行更新
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

    function deleted(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/activity/deleteActivity",{"activityId" : id},function(data){
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
        var activityName = $('#search_activityName').val();
        var startTime = $("#search_startTime").val();
        var status = $("#search_status").val();
        reload(activityName, startTime, status);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(activityName, startTime, status) {
        tableIns.reload({
            where: {
                activityName: activityName,
                startTime: startTime,
                status: status
            }
        });
    }
})