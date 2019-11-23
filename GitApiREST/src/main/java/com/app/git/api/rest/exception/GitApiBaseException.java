/**
 * 
 */
package com.app.git.api.rest.exception;

/**
 * @author M7597230
 * User Defined Exception
 */
public class GitApiBaseException extends Exception {

	String errorMsg;
	
	public GitApiBaseException(String msg){
		errorMsg = msg;
	}
	
	public String getMessage(){
		return errorMsg;
	}
	
}
