package utility;

import java.util.Iterator;
import java.util.List;

import model.User;

public class LoginValidation {
	
	
	
	public User loginValidation(List<User> userData, String email, String password) {
		
		Iterator<User> userIterator = userData.iterator();
		boolean flag = false;
		User user = null;
		while(userIterator.hasNext()) {
			user = userIterator.next();
			if(user.getEmailId().equalsIgnoreCase(email)
					&& user.getPassword().equals(password)) {
				flag = true;
				break;
				
			}
			else {
				user = null;
			}
			// System.out.println(userIterator.next().getEmailId());
			
		}
		return user;
	}
}
