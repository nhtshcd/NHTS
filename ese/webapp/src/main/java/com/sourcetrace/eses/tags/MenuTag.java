/*
 * MenuTag.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/*
 * MenuTag.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
import com.sourcetrace.eses.BreadCrumb;
import com.sourcetrace.eses.entity.Menu;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class MenuTag.
 *
 * @author $Author: moorthy $
 * @version $Rev: 3303 $, $Date: 2009-03-17 23:57:44 +0530 (Tue, 17 Mar 2009) $
 */
public class MenuTag extends TagSupport {

	private String name;
	private String dimension;
	Role role = new Role();
	private JspWriter out = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		List<Menu> menus = (List<Menu>) pageContext.getSession().getAttribute("menu");

		out = pageContext.getOut();

		int dimensionValue = Menu.DIMENSION_VERTICAL;
		try {
			dimensionValue = Integer.valueOf(this.dimension);
		} catch (Exception e) {
		}

		// if (dimensionValue == Menu.DIMENSION_VERTICAL) {
		printMenus(menus);
		// } else if (dimensionValue == Menu.DIMENSION_HORIZONTAL) {
		// printHorizontalMenu(menus);
		// }

		return (EVAL_PAGE);
	}

	private void printMenus(List<Menu> menus) {

		try {
			StringBuffer liParentMenuBuffer = new StringBuffer();
			liParentMenuBuffer.append("<ul class=\"navmenu\">");
			liParentMenuBuffer.append("<li class=\"menuToggle\"><a href=\"#\"><i class=\"fa fa-bars\" aria-hidden=\"true\"></i></a><div class=\"closeMnu\"><i class=\"fa fa-times\" aria-hidden=\"true\"></i></div></li>");
			if (!ObjectUtil.isListEmpty(menus)) {
				for (Menu menu : menus) {
					if (menu.isVerticalDimension()) {
						String menuURL = getMenuURL(menu);
						boolean parentHasSelfMenu = false;
						
						String className = StringUtil.isEmpty(menu.getMenuClassName()) ? "menuClass"
								: menu.getMenuClassName().replaceAll("\\.", "-");
						String iconClass = StringUtil.isEmpty(menu.getIconClass())
								? BreadCrumb.DEFAULT_FOLDER_ICON_CLASS : menu.getIconClass().trim();
						
						
						
						/*liParentMenuBuffer.append("<li class=\"" + className + "\">\n" + "<a href=\"" + menuURL
								+ "\"><i class=\"fa " + iconClass + "\"></i>\n" + "    <span class=\"title\"> "
								+ menu.getLabel() + " </span><span class=\"icon-arrow\"></span>\n"
								+ "    <span class=\"selected\"></span>\n" + "</a>\n");*/
						
						
					/*	
						liParentMenuBuffer.append("<li class=\"menuToggle\"><a href=\"#\"><i class=\"fa fa-bars\" aria-hidden=\"true\"></i></a></li>+"
					       +"<li class=\"" + className + "\">\n" + "<a href=\"" + menuURL
								+ "\"><i class=\"fa " + iconClass + "\"></i></a>\n");*/
						
						liParentMenuBuffer.append("<li class=\"" + className + "\">\n" + "<a href=\"" + menuURL
										+ "\"><i class=\"fa " + iconClass + "\"></i></a>\n");

						if (!ObjectUtil.isListEmpty(menu.getSubMenus())) {
							try {
								if (!parentHasSelfMenu) {
									liParentMenuBuffer.append("<ul class=\"submenu\">\n");
									liParentMenuBuffer.append(" <li class='menu-title'><i class=\"fa "+menu.getIconClass()+"\" aria-hidden='true'></i>"+menu.getLabel()+"</li>\n");
									for (Menu subMenu : menu.getSubMenus()) {
										liParentMenuBuffer.append(printSubMenus(subMenu));
									}
									liParentMenuBuffer.append("</ul>\n");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						liParentMenuBuffer.append("</li>");
					}
				}
			}
			liParentMenuBuffer.append("</ul>\n");
			out.println(liParentMenuBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String printSubMenus(Menu menu) {
		StringBuffer liStringBuffer = new StringBuffer();
		/*String className = StringUtil.isEmpty(menu.getMenuClassName()) ? "menuIco"
				: menu.getMenuClassName().replaceAll("\\.", "-");*/
		if (!ObjectUtil.isListEmpty(menu.getSubMenus())) {
			liStringBuffer.append("<li class=\"has-submenu\">\n" + "<a href=\"" + menu.getUrl() + "\">\n"
					+  menu.getLabel());
		}else{
			liStringBuffer.append("<li>\n" + "<a href=\"" + menu.getUrl() + "\">\n"
					+  menu.getLabel());
		}
		if (menu.getPriority() == Menu.PRIORITIES.NEW.ordinal()) {
			liStringBuffer.append("<span class=\"badge badge-new\">new</span>\n");
		}
		liStringBuffer.append("</a>\n");

		if (!ObjectUtil.isListEmpty(menu.getSubMenus())) {
			liStringBuffer.append("<ul class=\"sub-submenu\">\n");
			for (Menu subMenu : menu.getSubMenus()) {
				liStringBuffer.append(printSubMenus(subMenu));
			}
			liStringBuffer.append("</ul>\n");
		}
		liStringBuffer.append("</li>\n");
		return liStringBuffer.toString();
	}

	private String getMenuURL(Menu menu) {

		String url = "javascript:void(0)";
		if (!ObjectUtil.isEmpty(menu)) {
			url = menu.getUrl();
		}
		return url;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getDimension() {

		return dimension;
	}

	public void setDimension(String dimension) {

		this.dimension = dimension;
	}

}
