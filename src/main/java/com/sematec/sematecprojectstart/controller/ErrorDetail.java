package com.sematec.sematecprojectstart.controller;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class ErrorDetail {
	
	private String title;
	private int status;
	private String detail;
	private String timeStamp;
	private String developerMessage;
	private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
	

}
