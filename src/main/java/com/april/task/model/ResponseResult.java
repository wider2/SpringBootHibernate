package com.april.task.model;

public class ResponseResult {

    private boolean status;

    private String message;

    public ResponseResult() {
    	super();
	}

    public ResponseResult(boolean status, String message) {
    	super();
    	this.status = status;
    	this.message = message;
	}

	public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
