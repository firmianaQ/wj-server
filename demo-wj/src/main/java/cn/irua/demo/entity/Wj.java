package cn.irua.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Wj implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "wj_id", type = IdType.AUTO)
    private Long wjId;
    /**
     * 问卷类型
     */
    private String wjType;
    /**
     * 问卷标题
     */
    private String wjTitle;
    /**
     * 问卷简介
     */
    private String wjMemo;
    /**
     * 问卷所属用户
     */
    @TableField(exist=false)
    private Users user;
    
    private Long uid;
    /**
     * 问卷发布者别名
     */
    private String wjInitiator;
    /**
     * 问卷开始时间
     */
    private Date wjTime1;
    /**
     * 问卷结束时间
     */
    private Date wjTime2;
    
    /**
     * 0:未投放，1:正在回收，2:停止回收
     */
    private String wjState;
    /**
     * 问卷题目
     */
    @TableField(exist=false)
    private List<Wjtm> wjtms = new ArrayList<Wjtm>();
    /**
     * 答卷
     */
    @TableField(exist=false)
    private List<Dj> djs = new ArrayList<Dj>();
	public Long getWjId() {
		return wjId;
	}
	public void setWjId(Long wjId) {
		this.wjId = wjId;
	}
	public String getWjType() {
		return wjType;
	}
	public void setWjType(String wjType) {
		this.wjType = wjType;
	}
	public String getWjTitle() {
		return wjTitle;
	}
	public void setWjTitle(String wjTitle) {
		this.wjTitle = wjTitle;
	}
	public String getWjMemo() {
		return wjMemo;
	}
	public void setWjMemo(String wjMemo) {
		this.wjMemo = wjMemo;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getWjInitiator() {
		return wjInitiator;
	}
	public void setWjInitiator(String wjInitiator) {
		this.wjInitiator = wjInitiator;
	}
	public Date getWjTime1() {
		return wjTime1;
	}
	public void setWjTime1(Date wjTime1) {
		this.wjTime1 = wjTime1;
	}
	public Date getWjTime2() {
		return wjTime2;
	}
	public void setWjTime2(Date wjTime2) {
		this.wjTime2 = wjTime2;
	}
	public String getWjState() {
		return wjState;
	}
	public void setWjState(String wjState) {
		this.wjState = wjState;
	}
	public List<Wjtm> getWjtms() {
		return wjtms;
	}
	public void setWjtms(List<Wjtm> wjtms) {
		this.wjtms = wjtms;
	}
	public List<Dj> getDjs() {
		return djs;
	}
	public void setDjs(List<Dj> djs) {
		this.djs = djs;
	}
	@Override
	public String toString() {
		return "Wj [wjId=" + wjId + ", wjType=" + wjType + ", wjTitle=" + wjTitle + ", wjMemo=" + wjMemo + ", user="
				+ user + ", uid=" + uid + ", wjInitiator=" + wjInitiator + ", wjTime1=" + wjTime1 + ", wjTime2="
				+ wjTime2 + ", wjState=" + wjState + ", wjtms=" + wjtms + ", djs=" + djs + "]";
	}
	
}
