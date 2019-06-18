layui.use(['form','layer','jquery'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    $(".loginBody .seraph").click(function(){
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
            time:5000
        });
    })

    //登录按钮
    $(document).on('click', '#loginBtn', function () {
        var form = $(this).parents("form");
        var url = "/login";
        var serializeArray = form.serializeArray();
        $.post(url, serializeArray, function (data) {
            if (data.code == 200) {
                layer.msg("<em style='color:green'>"+data.message+"</em>", {
                    icon: 1,
                    offset: '30%',
                    time: 800
                },function () {
                    location.href = data.data.url;
                });
            }else{
                layer.msg("<em style='color:red'>"+data.message+"</em>", {
                    icon: 2,
                    offset: '30%',
                    time: 2000
                });
            }
        } );
    });

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
