/*
 * RoleEntitlementAction.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.Action;
import com.sourcetrace.eses.entity.Menu;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.JsonUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class RoleEntitlementAction.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RoleEntitlementAction extends ESEAction {

	private static final long serialVersionUID = -1853894718563599638L;

	private static final Logger LOGGER = Logger.getLogger(RoleEntitlementAction.class);

	private long selectedRole;
	private long selectedParentMenu;
	private List<Role> roles = new ArrayList<Role>();
	private Map<Long, String> parentMenus = new LinkedHashMap<Long, String>();
	private Map<String, Map<String, String>> listActionMenus;
	private List<String> entitlements = new ArrayList<String>();
	private String command;
	private String id;
	private String roleName;
	private String listActionMenusEmpty;
	private List<Action> entitlementActions;
	private Map<String, String> actionMap;

	private String branchId_F;

	@Autowired
	private IUtilService utilService;

	/**
	 * Sets the role service.
	 * 
	 * @param roleService
	 *            the new role service
	 */

	/**
	 * Gets the entitlements.
	 * 
	 * @return the entitlements
	 */
	public List<String> getEntitlements() {

		return entitlements;
	}

	/**
	 * Sets the entitlements.
	 * 
	 * @param entitlements
	 *            the new entitlements
	 */
	public void setEntitlements(List<String> entitlements) {

		this.entitlements = entitlements;
	}

	/**
	 * Gets the selected parent menu.
	 * 
	 * @return the selected parent menu
	 */
	public long getSelectedParentMenu() {

		return selectedParentMenu;
	}

	/**
	 * Sets the selected parent menu.
	 * 
	 * @param selectedParentMenu
	 *            the new selected parent menu
	 */
	public void setSelectedParentMenu(long selectedParentMenu) {

		this.selectedParentMenu = selectedParentMenu;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * Gets the command.
	 * 
	 * @return the command
	 */
	public String getCommand() {

		return command;
	}

	/**
	 * Sets the command.
	 * 
	 * @param command
	 *            the new command
	 */
	public void setCommand(String command) {

		this.command = command;
	}

	/**
	 * Gets the role name.
	 * 
	 * @return the role name
	 */
	public String getRoleName() {

		return roleName;
	}

	/**
	 * Sets the role name.
	 * 
	 * @param roleName
	 *            the new role name
	 */
	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	/**
	 * Gets the selected role.
	 * 
	 * @return the selected role
	 */
	public Long getSelectedRole() {

		return selectedRole;
	}

	/**
	 * Sets the selected role.
	 * 
	 * @param selectedRole
	 *            the new selected role
	 */
	public void setSelectedRole(Long selectedRole) {

		this.selectedRole = selectedRole;
	}

	/**
	 * Gets the list action menus empty.
	 * 
	 * @return the list action menus empty
	 */
	public String getListActionMenusEmpty() {

		return listActionMenusEmpty;
	}

	/**
	 * Sets the list action menus empty.
	 * 
	 * @param listActionMenusEmpty
	 *            the new list action menus empty
	 */
	public void setListActionMenusEmpty(String listActionMenusEmpty) {

		this.listActionMenusEmpty = listActionMenusEmpty;
	}

	/**
	 * Gets the list action menus.
	 * 
	 * @return the list action menus
	 */
	public Map<String, Map<String, String>> getListActionMenus() {

		return listActionMenus;
	}

	/**
	 * Sets the list action menus.
	 * 
	 * @param role
	 *            the role
	 * @param listActionMenus
	 *            the list action menus
	 */
	public void setListActionMenus(Role role, Map<String, Map<String, String>> listActionMenus) {

		Map<String, Map<String, String>> menuActions = new LinkedHashMap<String, Map<String, String>>();
		this.listActionMenus = menuActions;
		// To load Action Maps
		loadActionMap(listActionMenus);
		Integer isMultibranchApp = (Integer) request.getSession().getAttribute(ISecurityFilter.IS_MULTI_BRANCH_APP);

		Set<String> vals = listActionMenus.keySet();
		for (String val : vals) {
			Map<String, String> actionEntMap = listActionMenus.get(val);
			Set<String> actions = actionEntMap.keySet();
			Map<String, String> actionEntitlement = new LinkedHashMap<String, String>();
			Set<String> valsActions = actionMap.keySet();
			// Add action key sequentialy by default
			for (String action : valsActions) {
				actionEntitlement.put(getText(action), "");
			}

			for (String action : actions) {
				if (role.isDataFiltered()) {
					if (val.equals(ISecurityFilter.BRANCH_PROFILE) && action.equals(ISecurityFilter.DELETE)) {
						LOGGER.debug("removed action " + action);
					} else {
						actionEntitlement.put(getText(action), actionEntMap.get(action));
					}
				} else {
					actionEntitlement.put(getText(action), actionEntMap.get(action));
				}
			}
			if (isMultibranchApp == 1) {
				if ((StringUtil.isEmpty(getBranchId()) && !getBranchId_F().equals(""))
						|| !StringUtil.isEmpty(getBranchId())) {
					if (!val.equals("profile.branchMaster")) {
						menuActions.put(getText(val), actionEntitlement);
					}
				} else {
					menuActions.put(getText(val), actionEntitlement);
				}
			} else {
				menuActions.put(getText(val), actionEntitlement);
			}
		}
	}

	private void loadActionMap(Map<String, Map<String, String>> listActionMenus) {

		actionMap = new LinkedHashMap<String, String>();
		List<String> actionList = new ArrayList<String>();
		Set<String> vals = listActionMenus.keySet();
		for (String val : vals) {
			Map<String, String> actionEntMap = listActionMenus.get(val);
			Set<String> actions = actionEntMap.keySet();
			for (String action : actions) {
				// get action name from listActionMenu and add to list
				if (!actionList.contains(action)) {
					actionList.add(action);
				}
			}
		}
		// Add action map with ascending of Action entity id logic
		if (actionList.size() > 0 && getEntitlementActions().size() > 0) {
			for (Action action : getEntitlementActions()) {
				if (actionList.contains(action.getName())) {
					actionMap.put(action.getName(), action.getName());
				}
			}
		}

	}

	/**
	 * Execute.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		return super.execute();
	}

	/**
	 * List.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String list() throws Exception {

		roles = new ArrayList<Role>();
		if (!StringUtil.isEmpty(getBranchId())) {
			roles = utilService.listRoles();
		} else {
			if (!ObjectUtil.isEmpty(getBranchId_F()) && !getBranchId_F().equals("-1")) {
				roles = utilService.listRolesByTypeAndBranchId(getBranchId_F());
			}
		}
		List<Menu> parentMenuList = utilService.listParentMenus();
		if (!ObjectUtil.isListEmpty(parentMenuList)) {
			for (Menu menu : parentMenuList) {
				parentMenus.put(menu.getId(), getText(menu.getLabel()));
			}
		}

		setListActionMenus(utilService.findRole(getSelectedRole()),
				utilService.getSelectedSubMenusActions(getSelectedRole(), getSelectedParentMenu()));

		entitlements = utilService.getEntitlements(getSelectedRole(), getSelectedParentMenu());
		if (getListActionMenus().isEmpty()) {
			// set message if ListActionMenus Map list is empty
			setListActionMenusEmpty((getText("nomenu.assigned")));
		}
		command = "view";
		request.setAttribute("heading", getText("roleEntitlement.detail"));
		return "list";
	}

	public void populateRoles() {
		roles = new ArrayList<Role>();
		if (!branchId_F.equals("-1")) {
			roles = utilService.listRolesByTypeAndBranchId(getBranchId_F());
		}
		Map<String, String> map = ReflectUtil.buildMap(roles, new String[] { "id", "name" });
		sendAjaxResponse(JsonUtil.maptoJSONArrayMap(map));
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		String view = "input";
		roles = utilService.listRoles();
		/*
		 * if ((selectedRole == -1) && (selectedParentMenu == -1)) {
		 * addActionError(getText("emptyRoleParentMenu"));
		 * request.setAttribute(HEADING, getText("roleEntitlement.detail"));
		 * return list(); }
		 */

		if ("-1".equalsIgnoreCase(branchId_F)) {
			addActionError(getText("emptyOrganiztion"));
			request.setAttribute(HEADING, getText("rolemenu.list"));
			return list();
		} else if (selectedRole == -1) {
			addActionError(getText("emptyRole"));
			request.setAttribute(HEADING, getText("roleEntitlement.detail"));
			return list();
		} else if (selectedParentMenu == -1) {
			addActionError(getText("emptyParentMenu"));
			request.setAttribute(HEADING, getText("roleEntitlement.detail"));
			return list();
		} else {
			Role rName = utilService.findRole(getSelectedRole());
			 //branchId_F = rName.getBranchId();
			if (rName != null) {
				branchId_F = rName.getBranchId();
				setRoleName(rName.getName());
			} else {
				setListActionMenusEmpty((getText("nomenu.assigned")));
				request.setAttribute("heading", getText("roleEntitlement.detail"));
				return list();
			}

			List<Menu> parentMenuList = utilService.listParentMenus();
			if (!ObjectUtil.isListEmpty(parentMenuList)) {
				for (Menu menu : parentMenuList) {
					parentMenus.put(menu.getId(), getText(menu.getLabel()));
				}
			}
			setListActionMenus(utilService.findRole(getSelectedRole()),
					utilService.getSelectedSubMenusActions(getSelectedRole(), getSelectedParentMenu()));
			if (getListActionMenus().isEmpty()) {
				// set message if ListActionMenus Map list is empty
				setListActionMenusEmpty((getText("nomenu.assigned")));
			}
		
			if (StringUtil.isEmpty(id)) {
				entitlements = utilService.getEntitlements(getSelectedRole(), getSelectedParentMenu());

				request.setAttribute("heading", getText("editrole.entitlement"));
				
				LOGGER.debug("entitlements " + entitlements);
			} else {
				LOGGER.debug("selected entitlements " + entitlements);
				List<String> esits = utilService.getEntitlements(getSelectedRole(), getSelectedParentMenu());
				farmerService.runSQLQuery("delete es from ese_role_ent es join ese_ent e on e.id = es.ent_id and e.name in (:param1) and es.role_id=(:param2)",
						new Object[] { esits.stream().collect(Collectors.joining(",")) ,getSelectedRole()});
				Role role = utilService.editEntitlements(getSelectedRole(), getSelectedParentMenu(), entitlements);
				if (ObjectUtil.isEmpty(role)) {
					addActionError(getText("menusEmpty"));
				}
				view = LIST;
				request.setAttribute("heading", getText("roleEntitlement.detail"));

			}
		}

		return view;
	}

	/**
	 * Sets the parent menus.
	 * 
	 * @param parentMenus
	 *            the parent menus
	 */
	public void setParentMenus(Map<Long, String> parentMenus) {

		this.parentMenus = parentMenus;
	}

	/**
	 * Gets the parent menus.
	 * 
	 * @return the parent menus
	 */
	public Map<Long, String> getParentMenus() {

		return parentMenus;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Role> roles) {

		this.roles = roles;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public List<Role> getRoles() {

		return roles;
	}

	/**
	 * Gets the entitlement actions.
	 * 
	 * @return the entitlement actions
	 */
	public List<Action> getEntitlementActions() {

		if (ObjectUtil.isListEmpty(entitlementActions)) {
			entitlementActions = utilService.listAction();
		}
		return entitlementActions;
	}

	/**
	 * Sets the entitlement actions.
	 * 
	 * @param entitlementActions
	 *            the new entitlement actions
	 */
	public void setEntitlementActions(List<Action> entitlementActions) {

		this.entitlementActions = entitlementActions;
	}

	/**
	 * Gets the action map.
	 * 
	 * @return the action map
	 */
	public Map<String, String> getActionMap() {

		return actionMap;
	}

	public String getBranchId_F() {
		return branchId_F;
	}

	public void setBranchId_F(String branchId_F) {
		this.branchId_F = branchId_F;
	}

}
