package com.iteren.opsly.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iteren.opsly.model.ParsePageRequest;
import com.iteren.opsly.service.PageParserService;

@RestController
public class PageController {
	@Autowired
	private PageParserService pageParserService;

	@RequestMapping(method = RequestMethod.POST, path = "/rest/parsePage")
	public String getPageHierarchy(@Valid @RequestBody ParsePageRequest request) {
		return pageParserService.parsePageHierarchy(request.getUrl());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder str = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			str.append(fieldName).append(":").append(errorMessage).append("<br>");
		});
		return str.toString();
	}
}
