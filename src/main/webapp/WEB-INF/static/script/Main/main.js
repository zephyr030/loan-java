/**
 * 方法描述:控制面板
 *
 * author 小刘
 * version v1.0
 * date 2015/12/8
 */
$(function(){

    $(".navbar-buttons").click(function(){
       $(".user-menu").toggle();
    });

    $("#iframe_id").iFrameResize( [{
        log:true,
        enablePublicMethods:true
    }] );
});

function url(url){
    $("#iframe_id").attr("src",url);
}
