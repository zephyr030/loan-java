/**
 * 方法描述:提现回填
 *
 * author 小刘
 * version v1.0
 * date 2015/12/9
 */
function draw(id){
    top.art.dialog({
        content: $("#subForm").html(),
        lock:true,
        drag:true,
        opacity:0.1,
        ok: function () {
            //交易手数
            var countsNum = top.$("#countsNum").val();
            //金额
            var money = top.$("#money").val();
            //银行单号
            var bankNo = top.$("#bankNo").val();
            if(countsNum.length == 0 || parseInt(countsNum) == 0){
                top.$("#countsNumError").show();
                return false;
            }else if(!isMoney(money) || parseFloat(money) <= 0){
                top.$("#moneyError").show();
                return false;
            }else if(bankNo.length == 0){
                top.$("#bankNoError").show();
                return false;
            }else{
                top.$("#countsNumError").hide();
                top.$("#moneyError").hide();
                top.$("#bankNoError").hide();
                var status = 0;
                $.ajax({
                    type:"post",
                    url: "reloadDraw",
                    async: false,
                    data: {id:id,counts:countsNum,money:money,bankno:bankNo},
                    dataType: "json",
                    success: function(result){
                        if(parseInt(result.code) != 0){
                            status = 1;
                            top.$("#drawErrorr").html("错误提示:"+result.msg);
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

function refusedDraw(id){
    top.art.dialog({
        content: $("#remarkBox").html(),
        lock:true,
        drag:true,
        opacity:0.1,
        ok: function () {
            //备注
            var remark = top.$("#remark").val();
            var status = 0;
            $.ajax({
                type:"post",
                url: "refusedDraw",
                async: false,
                data: {id:id,remark:remark},
                dataType: "json",
                success: function(result){
                    if(parseInt(result.code) != 0){
                        status = 1;
                        top.$("#remarkErrorr").html("错误提示:"+result.msg);
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
        },
        cancelVal: '关闭',
        cancel: true
    });
}


