package com.chartroom.util;

import java.io.Serializable;

public class AjaxResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7716665587169147374L;
	
	/**
	 * 返回状态
	 */
	private String status;
	/**
	 * 返回消息
	 */
	private String msg;
	
	private AjaxResult(String status,String msg) {
		this.status=status;
		this.msg=msg;
	}
	
	/**
	 * 执行成功
	 * @param msg
	 * @return
	 */
	public static AjaxResult success(String msg){
		return new AjaxResult("success", msg);
	}
	
	/**
	 * 执行失败
	 * @param msg
	 * @return
	 */
	public static AjaxResult fail(String msg){
		return new AjaxResult("fail",msg);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
