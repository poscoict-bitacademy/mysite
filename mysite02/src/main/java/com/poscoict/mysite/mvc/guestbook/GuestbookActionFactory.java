package com.poscoict.mysite.mvc.guestbook;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("deleteform".equals(actionName)) {
			
		} else if("delete".equals(actionName)) {
			
		} else if("add".equals(actionName)) {
			
		} else {
			action = new IndexAction();
		}
		
		return action;
	}

}
