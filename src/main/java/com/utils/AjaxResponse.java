package com.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author zhouwei
 */
public class AjaxResponse {
    private Boolean success;     //返回状态 ：true成功, false失败
    private String message;      //返回信息
    private Object data;         //返回数据：返回时需要传回的数据，可以是一个对象，可以是一个集合
    private int code;            //返回状态标志： -1，未登录； 0，失败； 1，成功 （此参数为APP接口调用使用）

    public AjaxResponse() {
        this(Boolean.TRUE, "操作成功");
    }

    public AjaxResponse(Boolean success) {
        this(success, null);
    }

    public AjaxResponse(String message) {
        this(Boolean.TRUE, "操作成功");
    }

    public AjaxResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "操作失败";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }

        }
        
        if(Boolean.TRUE.equals(success)) {
        	this.code = 1;
        }
        if(Boolean.FALSE.equals(success)) {
        	this.code = 0;
        }
    }
    
    public AjaxResponse(Boolean success, String message, int code) {
        this.success = success;
        this.message = message;
        this.code = code;
        
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "操作失败";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }

        }
    }
    
    public AjaxResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "操作失败";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }
        }
    }
    
    public AjaxResponse(Boolean success, String message, int code, Object data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
        
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "操作失败";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }
        }
    }

    /**----------------------失败调用静态方法-----------------------------*/
    public static AjaxResponse fail() {
        return fail(null);
    }

    public static AjaxResponse fail(String message) {
        return new AjaxResponse(Boolean.FALSE, message);
    }
    
    public static AjaxResponse fail(String message, int code) {
        return new AjaxResponse(Boolean.FALSE, message, code);
    }
    
    public static AjaxResponse fail(String message, Object data) {
        return new AjaxResponse(Boolean.FALSE, message, data);
    }
    
    public static AjaxResponse fail(String message, int code, Object data) {
        return new AjaxResponse(Boolean.FALSE, message, code, data);
    }

    /**----------------------成功调用静态方法-----------------------------*/
    public static AjaxResponse success() {
        return success(null);
    }

    public static AjaxResponse success(String message) {
        return new AjaxResponse(Boolean.TRUE, message);
    }

    public static AjaxResponse success(String message, int code) {
        return new AjaxResponse(Boolean.TRUE, message, code);
    }
    
    public static AjaxResponse success(String message, Object data) {
        return new AjaxResponse(Boolean.TRUE, message, data);
    }
    
    public static AjaxResponse success(String message, int code, Object data) {
        return new AjaxResponse(Boolean.TRUE, message, code, data);
    }
    
    /**返回json字符串*/
    public String toJsonString() {
    	return JSON.toJSONString(this);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
    
    
}
