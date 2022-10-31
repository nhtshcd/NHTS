package com.sourcetrace.eses.dao;

import java.util.List;

public interface IESEDAO {

	/**
	 * Save.
	 * 
	 * @param obj
	 *            the obj
	 */
	public void save(Object obj);

	/**
	 * Save or update.
	 * 
	 * @param obj
	 *            the obj
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * Update.
	 * 
	 * @param obj
	 *            the obj
	 */
	public void update(Object obj);

	/**
	 * Delete.
	 * 
	 * @param obj
	 *            the obj
	 */
	public void delete(Object obj);

	/**
	 * List.
	 * 
	 * @param query
	 *            the query
	 * @return the list
	 */
	public List list(String query);

	/**
	 * List.
	 * 
	 * @param query
	 *            the query
	 * @param bindValues
	 *            the bind values
	 * @return the list
	 */
	public List list(String query, Object[] bindValues);

	/**
	 * Find.
	 * 
	 * @param query
	 *            the query
	 * @param value
	 *            the value
	 * @return the object
	 */
	public Object find(String query, Object value);

	/**
	 * Find.
	 * 
	 * @param query
	 *            the query
	 * @param values
	 *            the values
	 * @return the object
	 */
	public Object find(String query, Object[] values);
	
	public void removeBlindChilds(String tableName, String columnName,String tenantId);
}
