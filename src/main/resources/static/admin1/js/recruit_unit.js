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

    $.each(propertyData, function(index, item) {
        $('#property').append("<option value='" + item.value + "'>" + item.title + "</option>");
    });
    form.render();

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/recruit/queryRecruitUnit',
        toolbar: '#tool_unit',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'unitId', title: 'ID'}
            ,{field:'unitName', title: '单位名称', align: 'center'}
            ,{field:'industry', title: '所属行业', align: 'center'}
            ,{field:'property', title: '单位性质', align: 'center',
                templet: function (data) {
                    var proper;
                    $.each(propertyData, function (index, item) {
                        if(item.value == data.property) {
                            proper = item.title;
                        }
                    });
                    return proper;
                }}
            ,{field:'scale', title: '单位规模', align: 'center',
                templet: function (data) {
                    var scale_data;
                    $.each(scale, function (index, item) {
                        if(item.value === data.scale) {
                            scale_data = item.title;
                        }
                    });
                    return scale_data;
                }}
            ,{field:'unitWeb', title: '官方网址', align: 'center'}
            ,{field:'contractor', title: '联系人', align: 'center'}
            ,{field:'mobile', title: '电话', align: 'center'}
            ,{field:'status', title: '状态', align: 'center',
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
            del_unit(data, data.unitId);
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
                    $.post('/web/recruit/getRecruitUnit', {"unitId": data.unitId}, function (res) {
                       if(res.code === '2000') {
                           layero.find("#unitName").val(res.data.unitName);
                           layero.find("#unitName").attr("disabled", true);
                           layero.find("#industry").val(res.data.industry);
                           layero.find("option[value='"+data.property+"']").prop("selected",true);
                           layero.find("option[value='"+data.scale+"']").prop("selected",true);
                           layero.find("#unitWeb").val(res.data.unitWeb);
                           layero.find("#companyPhone").val(res.data.companyPhone);
                           layero.find("#contractor").val(res.data.contractor);
                           layero.find("#mobile").val(res.data.mobile);
                           layero.find("#status").val(res.data.status);
                           layero.find("#address").val(res.data.address);
                           layero.find("#direct").text(res.data.direct);
                           $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                           $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
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
                var recruitUnit = {};
                $(layero).find("input").each(function() {
                    recruitUnit[this.name] = this.value;
                });
                recruitUnit["deleted"] = $("input[name='deleted']:checked").val();
                recruitUnit["scale"] = $(layero).find("#scale").val();
                recruitUnit["property"] = $(layero).find("#property").val();
                recruitUnit["direct"] = $(layero).find("#direct").val();
                if(data != null) {
                    recruitUnit["unitId"] = data.unitId;
                }
                $.post('/web/recruit/saveOrUpdateUnit', recruitUnit, function (rec) {//得到数据提交到后端进行更新
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

    function del_unit(obj,id) {
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/recruit/deleteUnit",{"unitId" : id},function(data){
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