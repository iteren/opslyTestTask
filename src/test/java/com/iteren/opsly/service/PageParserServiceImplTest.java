package com.iteren.opsly.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.iteren.opsly.AppConfiguration;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Jsoup.class })
public class PageParserServiceImplTest {
	@Mock
	private AppConfiguration config;

	@Mock
	private Connection connection;
	
	@InjectMocks
	private PageParserService service = new PageParserServiceImpl();

	private static final String HTML = "<h1>text</h1><div></div><h2>h2</h2><h4>h4</h4>";
	private static final String EXPECTED_HTML = "<h1>text</h1><h2>h2</h2>";
	private static final String MUST_SUPPLY_A_VALID_URL = "Must supply a valid URL";
	private static final String EXCEPTION = "Custom Excetion Text";
	private static final String URL = "url";
	
	@Before
	public void setUp() {
		when(config.getOutputFormat()).thenReturn("<%s>%s</%s>");
		when(config.getExceptionFormat()).thenReturn("%s: %s");
	}
	
	@Test
	public void parsePageHierarchyTestNull() {
		String response = service.parsePageHierarchy(null);

		assertTrue(MUST_SUPPLY_A_VALID_URL.equals(response));
	}

	@Test
	public void parsePageHierarchyTestException() throws IOException {
		PowerMockito.mockStatic(Jsoup.class);
		PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
		Mockito.when(connection.get()).thenThrow(new IOException(EXCEPTION));

		String response = service.parsePageHierarchy(URL);
		
		assertTrue(EXCEPTION.equals(response));
	}
	
	@Test
	public void parsePageHierarchy() throws IOException {
		Document doc = new Document(URL);
		doc.append(HTML);
		PowerMockito.mockStatic(Jsoup.class);
		PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
		Mockito.when(connection.get()).thenReturn(doc);
		when(config.getHeadings()).thenReturn("h1, h2, h3");
		
		String response = service.parsePageHierarchy(URL);
		
		assertTrue(EXPECTED_HTML.equals(response));
	}
	
	@Test
	public void parsePageHierarchyAllTags() throws IOException {
		Document doc = new Document(URL);
		doc.append(HTML);
		PowerMockito.mockStatic(Jsoup.class);
		PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
		Mockito.when(connection.get()).thenReturn(doc);
		when(config.getHeadings()).thenReturn("h1, h2, h3, h4, div");

		String response = service.parsePageHierarchy(URL);
		
		assertTrue(HTML.equals(response));
	}
}
