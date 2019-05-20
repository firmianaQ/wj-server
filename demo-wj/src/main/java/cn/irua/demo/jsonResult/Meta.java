package cn.irua.demo.jsonResult;

/**
 * 
 * @author irua
 *	元数据类
 */
public class Meta {
	private boolean succ;
	private int code;
	private String msg;

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Meta(boolean succ, int code, String msg) {
		super();
		this.succ = succ;
		this.code = code;
		this.msg = msg;
	}

	public Meta(boolean succ, String msg) {
		super();
		this.succ = succ;
		this.msg = msg;
	}

	public Meta(boolean succ) {
		super();
		this.succ = succ;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
