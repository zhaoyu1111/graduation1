layui.use(['layer', 'table', 'form', 'jquery'], function () {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        form = layui.form;


    $.post('/web/system/getOperator', null, function (rec) {//得到数据提交到后端进行更新
        if (rec.code === "2000") {
            $("#presidentName").append("<option value=''>请选择</option>");
            $.each(rec.data, function(index, item) {
                $('#presidentName').append("<option value='" + item.operatorId + "'>" + item.operatorName + "</option>");
            });
        }
    }, 'json');
    form.render("select");

    var tableIns = table.render({
        elem: '#mytable',
        url: '/web/origin/queryAlumniAssociation',
        toolbar: '#association',
        height: 525,
        cols: [[
            {type:'checkbox'}
            ,{field:'associaId', title: 'ID', align: 'center'}
            ,{field:'associaName', title: '校友会名称', align: 'center'}
            ,{field:'presidentId', title: '会长ID', align: 'center'}
            ,{field:'presidentName', title: '会长名称', align: 'center'}
            ,{field:'address', title: '地址', align: 'center'}
            ,{field:'ctime',  title: '创建时间', align: 'center',
                templet: function (data) {
                    return createTime(data.ctime);
                }}
            ,{field:'deleted', title: '状态', align: 'center',
                templet:function (data) {
                    if(data.deleted == 0) {
                        return "正常";
                    }else {
                        return "冻结";
                    }
                }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo'}
        ]],
        done: function (res, curr, couunt) {
            $('.layui-table-box').find("[data-field = 'associaId']").css('display', 'none');
            $('.layui-table-box').find("[data-field = 'presidentId']").css('display', 'none');
        },
        page: true
    });

    table.on('tool(role)', function (obj) {
        var data = obj.data;
        var layEven = obj.event;

        if(layEven === 'edit') {
            edit(data, '编辑');
        } else if(layEven === 'del') {
            del_associa(data, data.associaId);
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
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#addassocia").html(),
            success: function (layero, index) {
                if(data != null) {
                    layero.find("#associaName").val(data.associaName);
                    layero.find("#address").val(data.address);
                    layero.find("option[value='"+data.presidentId+"']").prop("selected",true);
                    $("input[name=deleted][value='0']").attr("checked", data.deleted == 0 ? true : false);
                    $("input[name=deleted][value='1']").attr("checked", data.deleted == 1 ? true : false);
                    form.render();
                }else {
                    $("input:radio").removeAttr("checked");
                    form.render();
                }
            },
            yes: function (index, layero) {
                var association = {};
                $(layero).find("input").each(function() {
                    association[this.name] = this.value;
                });
                association["presidentId"] = $(layero).find("#presidentName").val();
                if(data != null) {
                    association["associaId"] = data.associaId;
                }
                $.post('/web/origin/saveOrUpdateAssocia', association, function (rec) {//得到数据提交到后端进行更新
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

    function del_associa(obj,id) {
        alert(1);
        if(null!=id){
            layer.confirm('您确定要删除吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/web/origin/deleteAssocia",{"associaId" : id},function(data){
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

    form.on('checkbox', function (data) {
        //$("input:checkbox").removeAttr("checked");
        $(data.elem).attr('checked', true);
    });

    $(document).on('click', '#add', function () {
        edit(null, "新增分会");
        form.render();
    });

    $(document).on('click', '#search',  function () {
        var associaName = $('#search_associaName').val();
        var address = $("#search_address").val();
        reload(associaName, address);
        form.render();
    });

    $(document).on('click', '#recover', function () {
        reload(null, null);
    });

    function reload(associaName, address) {
        tableIns.reload({
            where: {
                associaName: associaName,
                address: address
            }
        });
    }
});