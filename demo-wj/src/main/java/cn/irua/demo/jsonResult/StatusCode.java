package cn.irua.demo.jsonResult;

public enum StatusCode {
	FAILED(0, "操作失败"), 
	AUTH_ERROR(401, "认证失败"), 
	SYS_ERROR(500, "系统错误"), 
	PARAM_ERROR(400, "参数错误"), 
	UNKNOWN_ERROR(499, "未知错误");

	private StatusCode(int code, String message) {
		this.message = message;
		this.code = code;
	}

	private String message;
	private int code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
