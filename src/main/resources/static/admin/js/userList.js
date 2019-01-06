layui.use(['laydate', 'laypage', 'layer', 'table', 'upload', 'element', "jquery"], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , upload = layui.upload //上传
        , element = layui.element //元素操作

    //向世界问个好
    layer.msg('Hello World');

    //监听Tab切换
    element.on('tab(userTable)', function (data) {
        layer.tips('切换了 ' + data.index + '：' + this.innerHTML, this, {
            tips: 1
        });
    });

    //执行一个 table 实例
    table.render({
        elem: '#userTable'
        , height: 420
        , url: '/api/user/queryUserInfo' //数据接口
        , title: '用户表'
        , page: true //开启分页
        , toolbar: '#toolBar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , totalRow: true //开启合计
        , where: {}
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'studentId', title: 'ID', width: 80, sort: true, fixed: 'left', totalRowText: '合计：'}
            , {field: 'userName', title: '用户名', width: 80}
            , {field: 'className', title: '班级', width: 90, sort: true, totalRow: true}
            , {field: 'collegeName', title: '学院', width: 80, sort: true}
            , {field: 'majorName', title: '专业', width: 80, sort: true, totalRow: true}
            , {field: 'mobile', title: '手机号', width: 150}
            , {field: 'gender', title: '性别', width: 200}
            , {field: 'birthday', title: '生日', width: 100}
            , {field: 'email', title: '邮箱', width: 135, sort: true, totalRow: true}
            , {fixed: 'currentCity', title: '当前城市', width: 100}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.msg('添加');
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：' + checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.msg('查看操作');
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            layer.msg('编辑操作');
        }
    });

    laypage.render({
        elem: 'pageDemo' //分页容器的id
        ,count: 100 //总页数
        ,skin: '#1E9FFF' //自定义选中色值
        //,skip: true //开启跳页
        ,jump: function(obj, first){
            if(!first){
                layer.msg('第'+ obj.curr +'页', {offset: 'b'});
            }
        }
    });
})