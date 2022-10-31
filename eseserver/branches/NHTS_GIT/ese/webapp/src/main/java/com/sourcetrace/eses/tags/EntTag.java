/*
 * EntTag.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package com.sourcetrace.eses.tags;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class EntTag extends TagSupport {
	private String name;
	private String action;

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		Map<String, Integer> ents = (Map<String, Integer>) pageContext.getSession().getAttribute("ent");

		String action = getAction();
       JspWriter out = pageContext.getOut();
		try {
			Integer ent = ents.get(getName());
			Integer permission = ent;
			Integer request = null;
			Integer viewall =-1;

			if (action.equalsIgnoreCase("list")|| action.equalsIgnoreCase("detail")|| action.equalsIgnoreCase("data")|| action.equalsIgnoreCase("upload")|| action.equalsIgnoreCase("download")) {
				request = 0;
				viewall=8;
			}
				else if(action.equalsIgnoreCase("data")){
				    request = 0;
	                viewall=8;   
				}
			 else if (action.equalsIgnoreCase("create")) {
				request = 1;
			} else if (action.equalsIgnoreCase("update")) {
				request = 2;
			} else if (action.equalsIgnoreCase("delete")) {
				request = 4;
			} else {
				request =-1;
			}

			int access=permissionprovider(permission.intValue(), request,viewall);
			return access;


		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return (EVAL_PAGE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}







	public int permissionprovider(int per, int req,int view){
		int permission=per;
		int request=req;
		int viewall=view;

		if((permission==request)||(permission+request==permission)||(permission==viewall)){
			return EVAL_BODY_INCLUDE;
			}

		else if(((permission==3)||(permission==12))&& ((request==1)||request==2)){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==6)||(permission==15)&& ((request==2)||(request==4))){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==7)||(permission==16)&& ((request==1)||(request==2)||(request==4))){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==5)||(permission==14)&& ((request==1)||(request==4))){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==9)&& (request==1)){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==11)&& (request==2)){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==13)&& (request==4)){
			return EVAL_BODY_INCLUDE;
		}
		else if((permission==15)&& ((request==2)||(request==4))){
			return EVAL_BODY_INCLUDE;
		}
		else{
			return SKIP_BODY;
		}

	}

}