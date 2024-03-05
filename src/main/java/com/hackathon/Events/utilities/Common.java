package com.hackathon.Events.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	
	public static Date formatSch(String s) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return f.parse(s);
	}
	
}
