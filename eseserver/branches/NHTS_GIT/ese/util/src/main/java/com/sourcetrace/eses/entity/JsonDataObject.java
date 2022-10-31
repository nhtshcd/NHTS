package com.sourcetrace.eses.entity;



public class JsonDataObject implements java.io.Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String success;
	private String message;
	private Object data;


	
	public JsonDataObject(){
		
	}
	
    public JsonDataObject(Long id, String success, String message, Object data) {
		super();
		this.id = id;
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
