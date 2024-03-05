package com.hackathon.Events.utilities;

import java.util.Date;
import java.util.regex.Pattern;

import com.hackathon.Events.forms.BlogForm;
import com.hackathon.Events.forms.LoginForm;

public class Validator {
	
	public static String msg = "";
	
	public static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean validate(LoginForm l) {
		if(!validEmail(l.getEmail())) {
			msg = "Invalid Email";
		}
		else if("1".equals(l.getReg()) && !l.getCnfrmPass().equals(l.getPassword())){
			msg = "Confirm pass and pass not equal";
		}
		else {
			return true;
		}
		return false;
	}
	
	public static boolean validEmail(String s) {
		return Pattern.compile(emailRegex).matcher(s).matches();
	}
	
	public static boolean validate(BlogForm b) {
		if(b.getName() == null || !b.getName().matches("[A-Za-z ]+")) {
			msg = "invalid name";
		}
		else if(b.getContent() == null || b.getContent().isBlank()) {
			msg = "Invalid Content";
		}
		else if(b.getScheduleFlag()!=null && b.getScheduleFlag()) {
			if(b.getScheduleAt() == null) {
				msg = "Invalid Scheduled At Time";
				return false;
			}
			try {
				Date d = Common.formatSch(b.getScheduleAt());
				if(d.getTime() < System.currentTimeMillis()) {
					msg = "Upload DateTime should not be before current time";
					return false;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				msg = "Invalid Date Format";
				return false;
			}
			return true;
		}
		else {
			return true;
		}
		return false;
	}
	
}
