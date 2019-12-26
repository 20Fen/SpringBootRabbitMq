package com.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import java.net.UnknownHostException;

/**
 *  描述: 异常信息拦截
 */
@RestControllerAdvice
public class GlobalExceptionHandling {
	protected Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = {CustomException.class, BindException.class,RuntimeException.class,Exception.class, UnknownHostException.class, UnexpectedTypeException.class})
	@ResponseBody
	public AjaxResult handleException(Exception e) {
		log.error("-------------------------------------------------------");
		log.error("Exception:",e);
		AjaxResult result = new AjaxResult(AjaxResult.AJAX_STATUS_ERROR);
		if(e == null){
			result.setMessage("Global Exception");
			return result;
		}
		if(e instanceof CustomException) {
			CustomException cause = (CustomException)e;
			log.error("GlobalHandle CustomException", cause);
			result.setMessage(cause.getMessage());
			return result;
		}
		if(e instanceof RuntimeException){
			RuntimeException cause = (RuntimeException)e;
			log.error("GlobalHandle RuntimeException", cause);
			result.setMessage(cause.getMessage());
			return result;
		}
		BindingResult cause = checkException(e);

		if(cause == null || cause.getFieldError() == null || cause.getFieldError().getDefaultMessage() == null) {
			result.setMessage(e.getMessage());
		}else {
			result.setMessage(cause.getFieldError().getDefaultMessage());
		}
	    return result;
	}

	private BindingResult checkException(Exception e){
		BindingResult cause = null;
		if(e instanceof BindException){
			cause = (BindException)e;
			log.error("GlobalHandle BindException", e);
		}
		if(e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException ex = ((MethodArgumentNotValidException)e);
			cause = ex.getBindingResult();
			log.error("GlobalHandle MethodArgumentNotValidException", e);
		}

		return cause;
	}
}
