package cn.irua.demo.jsonResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author irua
 * 自定义结果集
 *
 */
@Getter
@Setter
@ToString
public class JsonResult {
	private static final String OK = "ok";  
    private static final String ERROR = "error";  
    private Meta meta;  
    private Object data;
    
    public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
     * 
     * @param code 状态码
     * @return
     */
    public JsonResult succ(int code) {  
        this.meta = new Meta(true,code, OK);  
        return this;  
    } 
    /**
     * 
     * @param code
     * @param message
     * @return
     */
    public JsonResult succ(int code,String message) {  
        this.meta = new Meta(true,code, message);  
        return this;  
    } 
    /**
     * 成功
     * @param data 返回数据
     * @param code 状态码
     * @return
     */
    public JsonResult succ(int code,Object data) {  
        this.meta = new Meta(true,code,OK);
        this.data = data;
        return this;  
    }
    
    /**
     * 
     * @param code
     * @param message
     * @param data
     * @return
     */
    public JsonResult succ(int code,String message,Object data) {  
        this.meta = new Meta(true,code, message); 
        this.data = data;
        return this;  
    } 

	/**
     * 失败
     * @param code 状态码
     * @return
     * 
     */
    public JsonResult failure(int code) {  
        this.meta = new Meta(false, code,ERROR);  
        return this;  
    } 
    
    /**
     * 失败
     * @param code 状态码
     * @param message 错误信息
     * @return
     */
    public JsonResult failure(int code,String message,Object data) {  
        this.meta = new Meta(false, code,message);  
        return this;  
    } 
}
