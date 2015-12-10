/**
 * 方法描述:用户管理
 *
 * author 小刘
 * version v1.0
 * date 2015/12/10
 */
function userLock(id,status,name){
    var text = status == 1?"激活":"锁定";
    $.ajax({
        type:"post",
        url: "login",
        data: {userId:id,status:status},
        dataType: "json",
        success: function(result){
            if(parseInt(result.code) != 0){
                top.art.dialog({
                    content: result.msg,
                    lock:true,
                    drag:true,
                    opacity:0.1,
                    ok: function () {}
                });
            }else{
                top.art.dialog({
                    content: "用户:["+name+"]已"+text,
                    lock:true,
                    drag:true,
                    opacity:0.1,
                    ok: function () {
                        window.location.href = window.location.href;
                    }
                });
            }
        }
    });
}
