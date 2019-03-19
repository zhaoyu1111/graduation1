layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var donationObject = [{
        "title" : "学校",
        "value" : 1
    },{
        "title": "学院",
        "value": 2
    },{
        "title": "个人",
        "value": 3
    }];

    $("#donationObject").append("<option value=''>请选择</option>");
    $("#search_object").append("<option value=''>请选择</option>");
    $.each(donationObject, function (index, item) {
        $("#donationObject").append("<option value='" + item.value + "'>" + item.title + "</option>");
        $("#search_object").append("<option value='" + item.value + "'>" + item.title + "</option>");
    })
    form.render();

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/activity/queryDonation',
        toolbar: '#tool_donation',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'donationId', title: 'ID'}
            ,{field:'donationName', title: '捐赠人姓名', width: 103, align: 'center'}
            ,{field:'donationMobile', title: '联系方式', width: 125, align: 'center'}
            ,{field:'goodsName', title: '物品名称', align: 'center'}
            ,{field:'amount', title: '数量', align: 'center'}
            ,{field:'objectName', title: '被赠名称', width: 116, align: 'center'}
            ,{field:'handleName', title: '处理人名称', width: 101, align: 'center'}
            ,{field:'donationObject',  title: '捐赠对象', align: 'center',
                templet: function (data) {
                    var object;
                    $.each(donationObject, function (index, item) {
                        if(item.value === data.donationObject) {
                            object = item.title;
                        }
                    });
                    return object;
                }}
            ,{field:'status',  title: '状态', align: 'center',
                templet: function (data) {
                    if(data.status == 1) {
                        return "待处理";
                    }else if(data.status == 2){
                        return "正在处理";
                    } else {
                        return "已完成";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo', width: 151}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'donationId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_donation(data, data.operatorId);
        } else if(layEven === 'deal') {
            deal(data.donationId);
        }
    });

    function edit(data, title){

        layer.open({
            type:1,
            title:title,
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#add_operator").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#donationName").val(data.donationName);
                    layero.find("#goodsName").val(data.goodsName);
                    layero.find("#donationMobile").val(data.donationMobile);
                    layero.find("#amount").val(data.amount);
                    layero.find("#objectName").val(data.objectName);
                    layero.find("#unitName").val(data.unitName);
                    layero.find("option[value='" + data.donationObject + "']").prop("selected", true);
                    form.render();
                    if(data.status == 2 || data.status == 3) {
                        layer.msg("该项目已完成，不能修改");
                        layero.find("#donationName").attr("disabled", true);
                        layero.find("#goodsName").attr("disabled", true);
                        layero.find("#donationMobile").attr("disabled", true);
                        layero.find("#amount").attr("disabled", true);
                        layero.find("#objectName").attr("disabled", true);
                        layero.find("#unitName").attr("disabled", true);
                        layero.find("donationObject").prop("disabled", true);
                    }
                }
            },
            yes: function (index, layero) {
                var donation = {};
                $(layero).find("input").each(function() {
                    donation[this.name] = this.value;
                });
                donation["donationObject"] = $(layero).find("#donationObject").val();
                if(null != data) {
                    donation["donationId"] = data.donationId;
                }
                $.post('/web/activity/saveOrUpdateDonation', donation, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null, null);
                        layer.close(index);
                        return false;
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
            }
        });
    }

    function del_donation(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/activity/deleteDonation",{"donationId" : id},function(data){
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

    function deal(id) {
        if(null!=id){
            layer.confirm('该项目已处理完成？', {
                btn: ['是的','还没'] //按钮
            }, function(){
                $.post("/web/activity/completeDonation",{"donationId" : id},function(data){
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
        var donationObject = $('#search_object').val();
        var status = $("#search_status").val();
        var donationName = $("#search_name").val();
        reload(donationObject, status, donationName);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null, null);
    });

    function reload(donationObject, status, donationName) {
        tableIns.reload({
            where: {
                donationName: donationName,
                donationObject: donationObject,
                status: status
            }
        });
    }
})