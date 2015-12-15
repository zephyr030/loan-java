/**
 * 方法描述:会员信息修改
 *
 * author 小刘
 * version v1.0
 * date 2015/12/11
 */
$(function(){

    $("#check").click(function(){
       if($("input[type='checkbox']").is(':checked')){
            $("#status").val(1);
       }else{
           $("#status").val(2);
       }
    });

    $('#form').bootstrapValidator({
            message: "输入的值无效",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                account: {
                    message:"输入的账号无效",
                    validators: {
                        notEmpty: {
                            message: "请输入账号"
                        },
                        stringLength: {
                            min: 2,
                            max: 30,
                            message: "账号长度为2-30"
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: "账号必须是数字或者字母"
                        }
                    }
                },
                customername: {
                    validators: {
                        notEmpty: {
                            message: "姓名不能为空"
                        }
                    }
                },
                cardnumber: {
                    validators: {
                        notEmpty: {
                            message: "银行卡号不能为空"
                        }
                    }
                },
                mobile:{
                    validators: {
                        notEmpty: {
                            message: "手机号码不能为空"
                        }
                    }
                }
            }
    })
    .on('success.form.bv', function(e) {
        // Prevent form submission
        e.preventDefault();

        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        // Use Ajax to submit form data
        $.post($form.attr('action'), $form.serialize(), function(result) {
            if(parseInt(result.code) == 0){
                top.art.dialog({
                    content: result.msg,
                    lock:true,
                    drag:true,
                    opacity:0.1,
                    ok: function () {
                        window.location.href = "userList";
                    }
                });
            }else{
                top.art.dialog({
                    content: result.msg,
                    lock:true,
                    drag:true,
                    opacity:0.1,
                    ok: function () {}
                });
            }
        }, 'json');
    });
});




