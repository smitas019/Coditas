/**
 * 
 */
package com.app.git.api.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author M7597230
 * User Defined Exception
 */
@Provider
public class GitApiBaseException extends Exception {

	String errorMsg;
	Integer errorCode;
	
	public GitApiBaseException(Integer errorCode,String msg){
		
		this.errorCode = errorCode;
		this.errorMsg = msg;
	}
	
	public String getMessage(){
		return errorMsg;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
