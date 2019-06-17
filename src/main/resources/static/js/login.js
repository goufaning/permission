layui.use(['form'], function() {
    var layer = layui.layer;
    var $ = layui.jquery;
    //监听提交
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
        } )
    });

});