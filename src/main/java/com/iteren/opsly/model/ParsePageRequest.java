package com.iteren.opsly.model;

import javax.validation.constraints.NotBlank;

public class ParsePageRequest {
	@NotBlank(message = "URL cannot be empty!")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
