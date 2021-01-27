package com.iteren.opsly.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteren.opsly.AppConfiguration;

@Service
public class PageParserServiceImpl implements PageParserService {

	@Autowired
	private AppConfiguration config;

	@Override
	public String parsePageHierarchy(final String pageUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(pageUrl).get();
		} catch (Exception e) {
			Throwable cause = e.getCause();
			return cause == null ? e.getMessage() : e.getMessage() + "<br>" + cause.getMessage();
		}

		Elements hTags = doc.select(config.getHeadings());
		StringBuilder str = new StringBuilder();
		hTags.forEach(e -> str.append(String.format(config.getOutputFormat(), e.nodeName(), e.html(), e.nodeName())));

		return str.toString();
	}

}
