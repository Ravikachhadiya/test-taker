package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupValidation {
	
	private static final String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private Pattern pattern;
	private Matcher matcher1,matcher2;
	
	public boolean checkUserDetails(String email,String password,String confirmPassword) {
		// System.out.println("Enter check your details");
		if(validEmail(email) && validPassword(password,confirmPassword))
			return true;
		else
			return false;
	}

	private boolean validPassword(String password, String confirmPassword) {
		
		
		 pattern = Pattern.compile(passwordPattern);
		 if(password.equals(confirmPassword)) {
		 
		 matcher1 = pattern.matcher(password);
		 matcher2 = pattern.matcher(confirmPassword);
		 //System.out.println(matcher1);
		//System.out.println(matcher2);
		  return (matcher2.matches() && matcher1.matches());
		 }
		return false;
		 
	}

	private boolean validEmail(String email) {
		
		 String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		 // System.out.println("Enter email validation");
		 return email.matches(regex);
	}
}
