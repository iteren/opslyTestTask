package com.iteren.opsly;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfiguration {
	private String headings;
	private String outputFormat;
	private String exceptionFormat;

	public String getHeadings() {
		return headings;
	}

	public void setHeadings(String headings) {
		this.headings = headings;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getExceptionFormat() {
		return exceptionFormat;
	}

	public void setExceptionFormat(String exceptionFormat) {
		this.exceptionFormat = exceptionFormat;
	}

}
