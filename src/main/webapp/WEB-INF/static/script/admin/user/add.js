/**
 * 方法描述:添加用户
 *
 * author 小刘
 * version v1.0
 * date 2015/12/17
 */
$(function(){
    $("#check").click(function(){
        if($("input[type='checkbox']").is(':checked')){
            $("#available").val(1);
        }else{
            $("#available").val(0);
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
                username: {
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
                password: {
                    validators: {
                        notEmpty: {
                            message: "密码不能为空"
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: "密码长度为6-20"
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: "账号必须是数字或者字母"
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
                            window.location.href = "sysUserList";
                        }
                    });
                }else{
                    top.art.dialog({
                        content: result.msg,
                        lock:true,
                        drag:true,
                        opacity:0.1,
                        ok: function () {
                            $("#sub").removeAttr("disabled");
                        }
                    });
                }
            }, 'json');
        });
})
