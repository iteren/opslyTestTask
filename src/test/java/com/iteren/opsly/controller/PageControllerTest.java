package com.iteren.opsly.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.iteren.opsly.AppConfiguration;
import com.iteren.opsly.model.ParsePageRequest;
import com.iteren.opsly.service.PageParserService;

@RunWith(MockitoJUnitRunner.class)
public class PageControllerTest {
	@Mock
	private PageParserService serviceMock;
	
	@Mock
	private AppConfiguration config;

	@InjectMocks
	private PageController controller = new PageController();

	private static final String EXPECTED = "response";
	private static final String URL = "url";

	@Before
	public void setUp() {
		when(serviceMock.parsePageHierarchy(URL)).thenReturn(EXPECTED);
		when(serviceMock.parsePageHierarchy(null)).thenReturn(null);
		when(config.getExceptionFormat()).thenReturn("%s: %s");
	}

	@Test
	public void getPageHierarchySuccess() {
		ParsePageRequest request = new ParsePageRequest();
		request.setUrl(URL);
		String response = controller.getPageHierarchy(request);

		assertTrue(EXPECTED.equals(response));
	}

	@Test
	public void getPageHierarchyNullTest() {
		String response = controller.getPageHierarchy(null);
		
		assertNull(response);
	}
	
	@Test
	public void handleValidationExceptionsNullTest() {
		String response = controller.handleValidationExceptions(null);
		
		assertTrue(PageController.UNKNOWN_ERROR.equals(response));
	}
	
	@Test
	public void handleValidationExceptions() {
		BindingResult bindingResult = new DirectFieldBindingResult(controller, "controller");
		bindingResult.addError(new FieldError("1", "2", "3"));
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
		
		String result = controller.handleValidationExceptions(ex);
		
		assertTrue("2: 3".equals(result));
	}

}
