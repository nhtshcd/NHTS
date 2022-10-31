package com.sourcetrace.eses.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateClass 
{
	 public static void main(String args[]){
		 SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		 String inputDate = "13/02/2018";
		 
		 try {
		       Date pubDate = myFormat.parse(inputDate);
		       Date currentDate = new Date();
		       System.out.println("Number of Days between dates: "
		       +daysBetween(currentDate,pubDate));
		 } catch (Exception e) {
		       e.printStackTrace();
		 }
	   }
	 private static long daysBetween(Date one, Date two) {
	        long difference =  (one.getTime()-two.getTime())/86400000;
	        return Math.abs(difference);
	    }



}
