layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    var propertyData = [{
        "title": "国企",
        "value": 1
    }, {
        "title": "私企",
        "value": 2
    }, {
        "title": "合资",
        "value": 3
    }, {
        "title": "其他",
        "value": 4
    }];

    var scale = [{
        "title": "50人以下",
        "value": 1
    }, {
        "title": "50-150人",
        "value": 2
    }, {
        "title": "150-500人",
        "value": 3
    }, {
        "title": "500-1000人",
        "value": 4
    }, {
        "title": "1000人以上",
        "value": 5
    }];

    var status = [{
        "title": "待审核",
        "value": 1
    }, {
        "title": "通过",
        "value": 2
    }, {
        "title": "未通过",
        "value": 3
    }];

    $.each(scale, function(index, item) {
        $('#scale').append("<option value='" + item.value + "'>" + item.title + "</option>");
    });
    form.render("select");

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/recruit/queryRecruitUnit',
        toolbar: '#tool_unit',
        height: 460,
        cols: [[
            {type:'checkbox'}
            ,{field:'unitId', title: 'ID'}
            ,{field:'unitName', title: '单位名称'}
            ,{field:'industry', title: '所属行业'}
            ,{field:'property', title: '单位性质',
                templet: function (data) {
                    var proper;
                    $.each(propertyData, function (index, item) {
                        if(item.value == data.property) {
                            proper = item.title;
                        }
                    });
                    return proper;
                }}
            ,{field:'scale', title: '单位规模',
                templet: function (data) {
                    var scale_data;
                    $.each(scale, function (index, item) {
                        if(item.value === data.scale) {
                            scale_data = item.title;
                        }
                    });
                    return scale_data;
                }}
            ,{field:'unitWeb', title: '官方网址'}
            ,{field:'contractor', title: '联系人'}
            ,{field:'mobile', title: '电话'}
            ,{field:'status', title: '状态',
                templet:function (data) {
                    if(data.status == 1) {
                        return "正在审核";
                    }else if(data.status == 2){
                        return "审核通过";
                    } else {
                        return "审核未通过";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'unitId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_operator(data, data.operatorId);
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
            content:$("#add_unit").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#operatorId").val(data.operatorId);
                    layero.find("#operatorId").attr("disabled", true);
                    layero.find("#operatorName").val(data.operatorName);
                    layero.find("#mobile").val(data.mobile);
                    $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                    $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
                    layero.find("option[value='"+data.roleId+"']").prop("selected",true);
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
                recruitUnit["scale"] = $("#scale").val();
                recruitUnit["property"] = $("#property").val();
                $.post('/web/recruit/saveOrUpdateUnit', recruitUnit, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code === "2000") {
                        layer.msg(rec.message);
                        reload(null, null);
                    } else {
                        layer.msg(rec.message);
                    }
                }, 'json');
                layer.close(index);
                return false;
            }
        });
    }

    function del_operator(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/system/deleteOperator",{"operatorId" : id},function(data){
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