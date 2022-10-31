package com.sourcetrace.eses.web.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Test {
	
	public static void main(String[] arr) {
	String jsoon ="{\"DEALER_EXPIRE_CG00295\":\"10\",\"DEALER_EXPIRE_CG00296\":\"11\",\"DEALER_EXPIRE_CG00297\":\"12\",\"DEALER_EXPIRE_CG00298\":\"13\"}";
	JsonObject js = new Gson().fromJson(jsoon, JsonObject.class);
	System.out.println(js.toString());



	
	}

}
