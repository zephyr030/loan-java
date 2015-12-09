//只允许输入数字
function InputNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if ((k >= 48 && k <= 57) || k == 9 || k == 8 || k == 0 || ( k >= 96 && k <= 105 ) || k == 9) {

    } else {
        if (window.event) {
            window.event.returnValue = false;
        } else {
            e.preventDefault();//for firefox
        }
    }
}

function LTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1) {
        var j = 0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}

function RTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
            i--;
        }
        s = s.substring(0, i + 1);
    }
    return s;
}

function Trim(str) {
    return RTrim(LTrim(str));
}


function isMultiple(value, divisor) {
    if(divisor == 0 || value == 0) {
        return false;
    }
    if(value % divisor != 0) {
        return false;
    }
    return true;
}

//是否是整数
function isNumeric(str) {
    var reg = new RegExp("^-?\\d+$");
    return (reg.test(str));
}

//是否全部为字符
function isChar(str) {  //if return true , number,otherwise char
    var reg = /\d/;
    return !reg.test(str);
}

//
function isDataOrChar(str) {
    var dc = /\w/;
    return dc.test(str);
}

function isTel(str) {
    var dc = /[0-9_-]/gi;
    return dc.test(str);
}

//获取长度，一个中文两个字符
String.prototype.length2 = function () {
    var cArr = this.match(/[^x00-xff]/ig);
    return this.length + (cArr == null ? 0 : cArr.length);
}

//是否包含特殊字符00000
function isSpecialChar(str) {
    var s = /[!@#$%^&*<>?\|()]/gi;
    return s.test(str);
}
//是否包含特殊字符00000
function address(str) {
    var s = /[!@$%^&*<>?\|()]/gi;
    return s.test(str);
}

function getCookie(name) {
    var sRE = "(?:; )?" + name + "=([^;]*);?";
    var oRE = new RegExp(sRE);

    if (oRE.test(document.cookie)) {
        return decodeURIComponent(RegExp["$1"]);
    } else {
        return null;
    }
}

function setCookie(sName, sValue, oExpires, sPath, sDomain, bSecure) {
    var sCookie = sName + "=" + encodeURIComponent(sValue);

    if (oExpires) {
        sCookie += "; expires=" + oExpires.toGMTString();
    }

    if (sPath) {
        sCookie += "; path=" + sPath;
    }

    if (sDomain) {
        sCookie += "; domain=" + sDomain;
    }

    if (bSecure) {
        sCookie += "; secure";
    }

    document.cookie = sCookie;
}

function isURL(str) {
    var result = str.match(/http:\/\/.+/);
    if (result == null) {
        return false;
    }
    return true;
}


//身份证格式验证
function checkIdcard(idcard) {
    var Errors = new Array(true, false, false, false, false);
    var area = {11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"}
    var idcard, Y, JYM;
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) return Errors[4];
    //身份号码位数及格式检验
    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 )) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
            } else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
            }
            if (ereg.test(idcard)) return Errors[0];
            else return Errors[2];
            break;
        case 18:
            //18位身份号码检测
            //出生日期的合法性检查
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0 )) {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
            } else {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
            }
            if (ereg.test(idcard)) {//测试出生日期的合法性
                //计算校验位
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
                    + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                    + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
                    + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                    + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
                    + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                    + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
                    + parseInt(idcard_array[7]) * 1
                    + parseInt(idcard_array[8]) * 6
                    + parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1);//判断校验位
                if (M == idcard_array[17]) return Errors[0]; //检测ID的校验位
                else return Errors[3];
            } else return Errors[2];
            break;
        default:
            return Errors[1];
            break;
    }
}

//验证用户名
function checkUsername(username) {
	var nameReg = /^[A-Za-z0-9_]{5,15}/;
	if(!nameReg.test(username)) {
		return false;
	}
	
	var c = username.substr(0,1)
	if(!/^[A-Za-z]{1}$/.test(c)) {
		return false;
	}
	return true;
}

//验证操盘账号
function checkAccount(account) {
    var nameReg = /^[A-Za-z0-9_-]{5,30}/;
    if(!nameReg.test(account)) {
        return false;
    }
    return true;
}

//验证密码
function checkPassword(password) {
	var reg = /^[0-9a-zA-Z`~!@#$%&*(),.<>:;]{6,20}/;
	return reg.test(password);
}

//验证邮箱格式

function checkEmail(email){
	//var emailReg = /^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$/
	var emailReg = /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/
	return emailReg.test(email)
}
//验证是否是数字（允许有小数）
function checkLittleNum(num) {
	var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
	return reg.test(num);
}
//验证是否是金钱格式
function isMoney(money) {
	var reg = /^\d{1,10}(\.\d{1,2})?$/;
	return reg.test(money)
}


//验证手机

function checkMoblie(num){
	var reg = /^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\d{8}$/;
	return reg.test(num)
}

function checkPhone(phone) {
	var reg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return reg.test(phone)
}

//验证是否是数字
function isNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if ((k >= 48 && k <= 57) || k == 9 || k == 37 || k == 39 || k == 8 || k == 0 || ( k >= 96 && k <= 105 )) {

    } else {
        if (window.event) {
            window.event.returnValue = false;
        } else {
            e.preventDefault();//for firefox
        }
    }
}

//验证是否为全中文
function isChinese(str) {
    var reg = /^[\u4e00-\u9fa5]+$/gi;
    return reg.test(str)
}
function isChineseandnumber(str) {
    var reg = /^[\d\u4e00-\u9fa5]+$/gi;
    return reg.test(str)
}
String.prototype.gblen = function () {
    var len = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
            len += 2;
        } else {
            len++;
        }
    }
    return len;
}
String.prototype.gbtrim = function (len, s) {
    var str = '';
    var sp = s || '';
    var len2 = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
            len2 += 2;
        } else {
            len2++;
        }
    }
    if (len2 <= len) {
        return this;
    }
    len2 = 0;
    len = (len > sp.length) ? len - sp.length : len;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
            len2 += 2;
        } else {
            len2++;
        }
        if (len2 > len) {
            str += sp;
            break;
        }
        str += this.charAt(i);
    }
    return str;
}

// gbtrim(len 截取长度，按英文字节长度计算， s截取后的省略字符，如"…" )  
// 备注： 这里中文字符都是当作两个长度来计算的，所以gbtrim中的len为10时，是显示最多5个汉字的。  
// 当汉字数大于5时，由于截取后加上“…”，所以只显示4个汉字。


/*
 * 自定义提示框
 *
 */
var Alert = {
    callback:null,                       //回调函数
    ico:{warning:"/images/room/warning.jpg", /* 警告 */
         error:"/images/error-ico.jpg",/*错误*/
         right:"/images/right-ico.jpg" /*正确*/
    },
    /*
     * 按钮动作
     */
    Button:{ok:{value:"确定",js:"Ok"},      /*确定*/
            cancel:{value:"取消",js:"Cancel"},  /*取消*/
            close:{value:"关闭",js:"Close"},   /*关闭*/
            rec:{value:"马上充值",js:"OnRec"},    /*充值*/
            login:{value:"马上登陆",js:"OnLogin"},  /*登陆*/
            reg:{value:"马上注册",js:"OnReg"},    /*注册*/
            OKPath:{value:"确定",js:"OKPath(obj)"}
    },
    /*
     * 显示提示框
     * @param obj 参数:{ico:提示图标类型,but:[按钮key,按钮key],msg,expands:{msg:,url:},t:标题,callback:回调函数,openwindow:点击按钮链接页面}
     * @param code  0错误  1正确 
     */
    Show:function(title,content,func,type){
        if($(".tanchu_denglu1").length > 0){
            $(".tanchu_denglu1").remove();
        }
        $(".zhezhaocs").show();
        $("#tishi_1").remove();
        var str = '';
        str += '<div id="tishi_1" class="tanchu_denglu1">';
        str += '<div class="tanchu_denglu_h1">';
        str += '<b>' + title + '</b><a onclick="Alert.Cancel()">×</a>';
        str += '</div><div class="dlbig_box1">';
        str += '<div class="ts_wz1">' + content + '</div>';
        if(type == 1){
        	str += '<div class="ts_wzs1"><a class="ts_quer1" href="javascript:void(0)" onclick="' + func + '">确认</a><a class="ts_quer1" href="javascript:void(0)" onclick="Alert.Cancel()">关闭</a></div></div></div>';  
        }else{
        	str += '<div class="ts_wzs1"><a class="ts_quer1" href="javascript:void(0)" onclick="' + func + '">确认</a></div></div></div>';  
        }
        $("body").append(str);
    },
    CmdCenter:function(cmd){
       var flag = this[cmd]();
       if(this.callback){
           eval(this.callback+'('+flag+')');
           this.callback = null;
       }
    },
    Ok:function(){
        this.RemoveWindow();
        if(this.callback){
        	callback
        }
        return true;
    },
    OKPath:function(obj){
    	 this.RemoveWindow();
    	 window.location.href=obj;
         if(this.callback){
         	callback
         }
         return true;
   },
    Cancel:function(){
      this.RemoveWindow();
      if(this.callback){
    	  Alert.callback()
      }
      return false;
    },
    Close:function(){
      this.RemoveWindow();
      $(".zhezhaocs").hide();
      return false;
    },
    /*
     * 关闭当前提示窗口
     */
    RemoveWindow:function(){
        $(".tanchu_denglu1").remove();
        $(".zhezhaocs").hide();
    }
}
/**
 * 金额格式化
 */
function formatMoney(s, type) {
	if (/[^0-9\.]/.test(s))
		return "0";
	if (s == null || s == "")
		return "0";
	s = s.toString().replace(/^(\d*)$/, "$1.");
	s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
	s = s.replace(".", ",");
	var re = /(\d)(\d{3},)/;
	while (re.test(s))
		s = s.replace(re, "$1,$2");
	s = s.replace(/,(\d\d)$/, ".$1");
	if (type == 0) {// 不带小数位(默认是有小数位)
		var a = s.split(".");
		if (a[1] == "00") {
			s = a[0];
		}
	}
	return s;
}

var gDialog = {
	callback:null,                       //回调函数
    ico:{warning:"/images/room/warning.jpg", /* 警告 */
         error:"/images/error-ico.jpg",/*错误*/
         right:"/images/right-ico.jpg" /*正确*/
    },
    /*
     * 按钮动作
     */
    Button:{ok:{value:"确定",js:"Ok"},      /*确定*/
            cancel:{value:"取消",js:"Cancel"},  /*取消*/
            close:{value:"关闭",js:"Close"},   /*关闭*/
            rec:{value:"马上充值",js:"OnRec"},    /*充值*/
            login:{value:"马上登陆",js:"OnLogin"},  /*登陆*/
            reg:{value:"马上注册",js:"OnReg"}     /*注册*/
    },
	Show:function(title,content,btnName,url,func){
		if($(".tanchu_denglu1").length > 0){
            $(".tanchu_denglu1").remove();
        }
		
		 $(".zhezhaocs").show();
		 $("#tishi_1").remove();
		 var str = '<div id="tishi_1" class="tanchu_denglu1">';
	        str += '<div class="tanchu_denglu_h1">';
	        str += '<b>' + title + '</b><a onclick="gDialog.Cancel()">×</a>';
	        str += '</div><div class="dlbig_box1">';
	        if(content != null && content != ""){
	        	str += '<div class="ts_wz1">' + content + '</div>';
	        }
	        //str += '<div class="ts_wzs1"><a class="ts_quer1" href="javascript:void(0)" onclick="' + func + '">确认</a><a class="ts_quer1" href="javascript:void(0)" onclick="Alert.Cancel()">关闭</a></div></div></div>';  
	        str += '<div class="dlbig_box1"><a class="ts_anniu1" href="'+url+'">'+btnName+'</a><a class="ts_a1" onclick="'+func+'">继续购物</a></div></div></div>'; 
	        $("body").append(str);
	   },
	   CmdCenter:function(cmd){
	       var flag = this[cmd]();
	       if(this.callback){
	           eval(this.callback+'('+flag+')');
	           this.callback = null;
	       }
	    },
	   Cancel:function(){
		      this.RemoveWindow();
		      if(this.callback){
		    	  gDialog.callback()
		      }
		      return false;
	   },
	   Ok:function(){
	        this.RemoveWindow();
	        if(this.callback){
	        	callback
	        }
	        return true;
	    },
	    Close:function(){
	      this.RemoveWindow();
	      return false;
	    },
	    /*
	     * 关闭当前提示窗口
	     */
	    RemoveWindow:function(){
	        $(".tanchu_denglu1").remove();
	        $(".zhezhaocs").hide();
	    }
}
