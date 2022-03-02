package com.poscoict.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscoict.mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void ExceptionHandler(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e) throws Exception {
		
		//1. 로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOGGER.error(errors.toString());

		//2 요청 구분
		// JSON 요청인 경우: request header의 accecpt에 application/json
		// HTML 요청인 경우: request header의 accecpt에 text/html
		
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application/json.*")) {
			//3. JSON 응답
			JsonResult result = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(result);
			
			response.setContentType("application/json; charset=UTF-8");
			
			OutputStream os  = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
		} else {
			//4. 사과페이지(HTML 응답, 정상 종료)
			request.setAttribute("exception", errors.toString());
			request
				.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
				.forward(request, response);
		}
	}
}
