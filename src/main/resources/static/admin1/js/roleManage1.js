/**
 * 角色管理
 */
//'formSelects.render('permissions');
var pageCurr;
var form;

$(function() {

    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;

        tableIns=table.render({
            elem: '#mytable',
            url:'/web/system/listRole',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            cols: [[
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
                ]],
            done: function(res, curr, count){
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(role)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                delRole(data,data.roleId);
            } else if(obj.event === 'edit'){
                //编辑
                edit(data, "编辑");
            }
        });

        //监听提交
        form.on('submit(roleSubmit)', function(data){
            formSubmit(data);
            return false;
        });

    });

});

//提交表单
function formSubmit(obj){
    $.ajax({
        type: "post",
        data: $("#roleForm").serialize(),
        url: "/role/setRole",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.msg);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                load(obj);
            });
        }
    });
}

//新增
function add() {
    edit(null,"新增");
}

//打开编辑框
function edit(data,title){
    //var pid = null;
    if(data == null){
        $("#id").val("");
    }else{
        //回显数据
        $("#roleName").val(data.roleName);
    }

    /*formSelects.data('permissions', 'server', {
        url: '/permission/parentPermissionList',
        keyName: 'name',
        keyVal: 'id',
        success: function(id, url, searchVal, result){      //使用远程方式的success回调

            console.log(pid)
            if(pid != null){
                var assistAuditArry =pid.split(",");
                formSelects.value('permissions', assistAuditArry);
            }

            console.log(result);    //返回的结果
        },
        error: function(id, url, searchVal, err){           //使用远程方式的error回调
                                                            //同上
            console.log(err);   //err对象
        },
    });*/


    /*var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);*/

    layer.open({
        type: 1,
        area: ["30%"],
        btn: ['确认', '取消'],//弹出层按钮
        shade: 0.4,
        title: title,
        skin: "myclass",
        offset: '50px',
        content:$("#addUserForm").html(),
        yes: function (index, layero) {//点击“确认”按钮后触发的事件
                var role = {};
                //var body = layer.getChildFrame('body', index);
                var form = $("#addUserForm").serializeArray();//获取指定id的表单
                alert(form.name);
                $.each(form, function (i, n) {
                    alert(n.name);
                    alert(n.value);
                    role[this.name] = this.value;
                    /*alert(this.name);
                    alert(this.value);*/
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
            },
        end:function(){
            cleanRole();
        }
    });
}

//重新加载table
function load(obj){
    tableIns.reload({
        where: obj.f
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

//删除
function delRole(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/web/system/deleteRole",{"roleId" : id},function(data){
                if (data.code == "2000") {
                    layer.alert(data.message,function(){
                        layer.closeAll();
                        load(obj);
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
//恢复
function recoverRole(obj,id) {
    if(null!=id){
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/role/updateRoleStatus",{"id":id,"status":1},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}


function cleanRole() {
    $("#roleName").val("");
    $("#deleted").attr("checked", false);
    //$("#permissions").val("");
}

