package com.poscoict.mysite.mvc.user;

import com.poscoict.mysite.mvc.main.MainAction;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("joinform".equals(actionName)) {
			action = new JoinFormAction();
		} else if("join".equals(actionName)) {
			action = new JoinAction();
		} else if("joinsuccess".equals(actionName)) {
			action = new JoinSuccessAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}
