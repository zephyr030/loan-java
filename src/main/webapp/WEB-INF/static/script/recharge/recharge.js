/**
 * 方法描述:充值回填方法
 *
 * author 小刘
 * version v1.0
 * date 2015/12/9
 */
function isSend(id){
    top.art.dialog({
        content: $("#subForm").html(),
        lock:true,
        drag:true,
        opacity:0.1,
        ok: function () {
            //流水号
            var fowNo = top.$("#rechargeNo").val();
            //操作时间
            var time = top.$("#rechargeTime").val();
            if(fowNo.length == 0){
                top.$("#fowError").show();
                return false;
            }else if(time.length == 0){
                top.$("#timeError").show();
                return false;
            }else{
                top.$("#fowError").hide();
                top.$("#timeError").hide();
                var status = 0;
                $.ajax({
                    type:"post",
                    url: "reLoadCharge",
                    async: false,
                    data: {id:id,fowNo:fowNo,time:time},
                    dataType: "json",
                    success: function(result){
                        if(parseInt(result.code) != 0){
                            status = 1;
                            top.$("#rechargeError").html("错误提示:"+result.msg);
                        }else{
                            status = 0;
                        }
                    }
                });
                if(status == 0){
                    window.location.href = window.location.href;
                }else {
                    return false;
                }
            }
        },
        cancelVal: '关闭',
        cancel: true
    });
}
