layui.use(['element','form','jquery','laydate','table', 'laytpl'],function(){
    var element=layui.element
        ,form = layui.form
        ,$=layui.jquery
        ,laydate = layui.laydate
        ,laytpl = layui.laytpl;

    //日期初始化
    laydate.render({
        elem: '#startdate'
    });
    laydate.render({
        elem: '#enddate'
    });

    var table = layui.table;

    table.render({
        elem: '#mytable'
        ,url: "/web/system/listRole"
        ,cols: [[
            {type:'checkbox'}
            ,{field:'roleId', title: 'ID', sort: true}
            ,{field:'roleName', title: '角色名称'}
            ,{field:'ctime',  title: '创建时间', sort: true,
            templet: function (data) {
                return createTime(data.ctime);
            }}
            ,{field:'remark', title: '备注'}
            ,{field:'deleted', title: '状态',
            templet:function (data) {
                if(data.deleted == 0) {
                    return "正常";
                }else {
                    return "冻结";
                }
            }}
            ,{fixed: 'right',title:"操作",align:'center', toolbar: '#barDemo',width:165}
        ]]
        ,page: true
    });

    //监听工具条
    table.on('tool(operation)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'set'){ //查看
            //弹出一个tab层，用于设置用户组的人员和用户组的角色
            layer.tab({
                area: ['800px', '600px'],
                tab: [{
                    title: '设置角色成员',
                    content: '内容1'
                },{
                    title: '设置角色用户组',
                    content: '内容2'
                },{
                    title: '角色赋权',
                    content: '内容3'
                }]
            });
        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                //向服务端发送删除指令
                $.post("/web/system/deleteRole", {"roleId" : data.roleId}, function (res) {
                    if(res.code == 2000) {
                        layui.msg("删除成功")
                    }else {
                        layui.msg(res.message);
                        return ;
                    }
                });
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something
            //$("#roleName").attr("value", 1);
            x_content_edit("编辑", $("#addrole").html(), data);
            //同步更新缓存对应的值
            obj.update({
                roleName: data.roleName
                ,deleted: data.deleted
            });

            /* 渲染表单 */
            form.render();
        }
    });


    //新增按钮的点击事件
    $("#addBtn").click(function(){

        /* 再弹出添加界面 */
        layer.open({
            type:1,
            title:"新增角色",
            skin:"myclass",
            area:["30%"],
            btn: ['确认', '取消'],//弹出层按钮
            content:$("#addrole").html(),
            success: function (layero, index) {
                form.val("addoredit", {
                    "roleName": ""
                });
            },
            //url:"/web/system/addRole"
            yes: function (index, layero) {//点击“确认”按钮后触发的事件
                var role = {};
                //var body = layer.getChildFrame('body', index);
                var form = $("#addUserForm").serializeArray();//获取指定id的表单
                $.each(form, function () {
                    role[this.name] = this.value;
                });
                //data = JSON.stringify(data);
                //var content = {'role': data};
                $.post('/web/system/addRole', role, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code == "2000") {
                        layer.alert(rec.message, {icon: 6}, function (index) {
                            layer.close(index);
                        });
                        layui.use('table', function () {
                            var table = layui.table;
                            table.reload('role', {
                                url: '/web/system/listRole' //数据接口，更新成功后刷新数据表格
                            });
                        });
                    } else {
                        layer.alert(rec.message, {icon: 5}, function (index) {
                            layer.close(index);
                        });
                    }
                    layer.close(index);
                }, 'json');
            }
        });


        /* 渲染表单 */
        form.render();

    });

    function x_content_edit(title, url, data) {
        if (title == null || title == '') {
            title = false;
        }
        if (url == null || url == '') {
            url = "404.html";
        }

        layer.open({
            type: 1,
            area: ["30%"],
            //fix: false, //不固定
            btn: ['确认', '取消'],//弹出层按钮
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            skin: "myclass",
            offset: '50px',
            content: url,
            success: function (layero, index) {//弹出层打开后的回调函数
                //var body = layer.getChildFrame('body', index);//获取弹出层的dom元素
                result = JSON.stringify(data);
                result = $.parseJSON(result);
                $.each(result, function (item) {

                    //body.find('#A_' + item).val(result[item]);//给弹出层页面赋值，id为对应弹出层表单id
                    if (item == 'roleId') {
                        //body.find('#B_contentId').val(result[item]);//这里是为动态select赋值，在弹出层创建隐藏元素
                    } else if (item == 'roleName') {
                        $("#roleName").attr("value", result[item]);
                    } else if (item == 'deleted') {
                        var status = result[item];
                        $(":radio[name=deleted][value=status]").attr("checked","true");
                    }
                });
            },
            yes: function (index, layero) {//点击“确认”按钮后触发的事件
                var role = {};
                //var body = layer.getChildFrame('body', index);
                var form = $("#addUserForm").serializeArray();//获取指定id的表单
                $.each(form, function () {
                    role[this.name] = this.value;
                });
                role["roleId"] = data.roleId;
                //data = JSON.stringify(data);
                //var content = {'role': data};
                $.post('/web/system/updateRole', data, function (rec) {//得到数据提交到后端进行更新
                    if (rec.code == 2000) {
                        layer.alert(rec.message, {icon: 6}, function (index) {
                            layer.close(index);
                        });
                        layui.use('table', function () {
                            var table = layui.table;
                            table.reload('operation', {
                                url: '/web/system/listRole' //数据接口，更新成功后刷新数据表格
                            });
                        });
                    } else {
                        layer.alert(rec.message, {icon: 5}, function (index) {
                            layer.close(index);
                        });
                    }
                    layer.close(index);
                }, 'json');
                return false;

            }
        });
    }

    function getobj() {
        var elms = $("#addUserForm[name]"); //formid 包含name属性的所有元素
        var obj = {};
        $.each(elms, function (i, item) {
            var name = $(item).attr("name");
            obj[name] = "";
        });
        alert(JSON.stringify(obj));
        return obj;
    }
});

