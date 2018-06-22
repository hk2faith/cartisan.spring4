/*
 * @(#)ResourceResolver.java $version 2011. 08. 16.
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package us.cartisan.core.util;

public class ResourceResolver {
	private String templateClasspath = "";
	private String fileName = "";

	public ResourceResolver(String resourceClasspath) {
		if (resourceClasspath.indexOf("/") > -1) {
			fileName = resourceClasspath.substring(resourceClasspath.lastIndexOf("/") + 1, resourceClasspath.length());
			templateClasspath = resourceClasspath.substring(0, resourceClasspath.lastIndexOf("/"));

			if (templateClasspath.equals("")) {
				templateClasspath = "/";
			}
		} else {
			fileName = resourceClasspath;
			templateClasspath = "/";
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getTemplateClasspath() {
		return templateClasspath;
	}
}
