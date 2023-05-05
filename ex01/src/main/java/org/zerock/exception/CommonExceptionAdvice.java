package org.zerock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	// 500 에러페이지  -> @ExceptionHadler로 처리됨
	// 404 에러페이지 -> 다른 방식으로 처리하는것이 좋음( DispatcherServlet에서 처리되도록 함 /web.xml에 코드 입력)
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception .... " + ex.getMessage());
		model.addAttribute("exception", ex);
		log.error(model);
		
		return "error_page";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {	
		return "custom404";
	}
}
