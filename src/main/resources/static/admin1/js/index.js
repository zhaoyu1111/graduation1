var $,tab,skyconsWeather;
layui.config({
    base : "js/"
}).use(['bodyTab','form','element','layer','jquery'],function(){
    var form = layui.form,
        layer = layui.layer,
        element = layui.element;
    $ = layui.jquery;
    tab = layui.bodyTab();

    // 添加新窗口
    $(".layui-nav .layui-nav-item a").on("click",function(){
        addTab($(this));
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    })

    //公告层
    function showNotice(){
        layer.open({
            type: 1,
            title: "系统公告", //不显示标题栏
            closeBtn: false,
            area: '310px',
            shade: 0.8,
            id: 'LAY_layuipro', //设定一个id，防止重复弹出
            btn: ['火速围观'],
            moveType: 1, //拖拽模式，0或者1
            content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>最近发现贤心大神的layui框架，瞬间被他的完美样式所吸引，决定做一套基于layui框架的后台管理模板，以便以后自己开发时套用，在网上搜了很多基于layui的模板，有些需要收费，有些开源，但代码结构不是很适合自己，于是自己在别人开源代码的基础上开发了属于自己的后台管理模板。</p><p>在此特别感谢网络上开源自己后台管理模板代码的朋友，我也希望自己的后台模板能让更多的朋友看到，后续也会加入自己开发的一些功能。</p></div>',
            success: function(layero){
                var btn = layero.find('.layui-layer-btn');
                btn.css('text-align', 'center');
                btn.on("click",function(){
                    window.sessionStorage.setItem("showNotice","true");
                })
                if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
                    btn.on("click",function(){
                        layer.tips('系统公告躲在了这里', '#showNotice', {
                            tips: 3
                        });
                    })
                }
            }
        });
    }
    //刷新后还原打开的窗口
    if(window.sessionStorage.getItem("menu") != null){
        menu = JSON.parse(window.sessionStorage.getItem("menu"));
        curmenu = window.sessionStorage.getItem("curmenu");
        var openTitle = '';
        for(var i=0;i<menu.length;i++){
            openTitle = '';
            if(menu[i].icon.split("-")[0] == 'icon'){
                openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
            }else{
                openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
            }
            openTitle += '<cite>'+menu[i].title+'</cite>';
            openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
            element.tabAdd("bodyTab",{
                title : openTitle,
                content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                id : menu[i].layId
            })
            //定位到刷新前的窗口
            if(curmenu != "undefined"){
                if(curmenu == '' || curmenu == "null"){  //定位到后台首页
                    element.tabChange("bodyTab",'');
                }else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                    element.tabChange("bodyTab",menu[i].layId);
                }
            }else{
                element.tabChange("bodyTab",menu[menu.length-1].layId);
            }
        }
    }

})

//打开新窗口
function addTab(_this){
    tab.tabAdd(_this);
}
