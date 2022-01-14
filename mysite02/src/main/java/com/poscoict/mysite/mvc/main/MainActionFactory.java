package com.poscoict.mysite.mvc.main;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class MainActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new MainAction();
	}

}
