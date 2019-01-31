package com.helix.datatrail.mapper.util;


abstract public class SqlBuilderUtils
{
	private static final String FORMAT_PREFIX = "#{parameters.";
	private static final String FORMAT_SUFFIX = "}";
	
	public static String buildValueFormat(String fieldName)
	{
		StringBuilder formatBuilder = new StringBuilder();
		formatBuilder.append(FORMAT_PREFIX).append(fieldName).append(FORMAT_SUFFIX);
		return formatBuilder.toString();
	}
	
}
