package cn.irua.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Wjtm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "wjtm_id", type = IdType.AUTO)
    private Long wjtmId;
    @TableField(exist=false)
    private Wj wj;
    
    private Long wjId;
    private String wjtmType;

    private String wjtmTitle;

    private String wjtmMemo;

    private String wjtmRequired;

    private Integer wjtmScore;
    @TableField(exist=false)
    private List<Tmxx> tmxxs = new ArrayList<Tmxx>();
    @TableField(exist=false)
    private Stat_Info stat_info;
    public Stat_Info getStat_info() {
		return stat_info;
	}

	public void setStat_info(Stat_Info stat_info) {
		this.stat_info = stat_info;
	}
	public Long getWjtmId() {
		return wjtmId;
	}

	public void setWjtmId(Long wjtmId) {
		this.wjtmId = wjtmId;
	}

	public Wj getWj() {
		return wj;
	}

	public void setWj(Wj wj) {
		this.wj = wj;
	}

	public Long getWjId() {
		return wjId;
	}

	public void setWjId(Long wjId) {
		this.wjId = wjId;
	}

	public String getWjtmType() {
		return wjtmType;
	}

	public void setWjtmType(String wjtmType) {
		this.wjtmType = wjtmType;
	}

	public String getWjtmTitle() {
		return wjtmTitle;
	}

	public void setWjtmTitle(String wjtmTitle) {
		this.wjtmTitle = wjtmTitle;
	}

	public String getWjtmMemo() {
		return wjtmMemo;
	}

	public void setWjtmMemo(String wjtmMemo) {
		this.wjtmMemo = wjtmMemo;
	}

	public String getWjtmRequired() {
		return wjtmRequired;
	}

	public void setWjtmRequired(String wjtmRequired) {
		this.wjtmRequired = wjtmRequired;
	}

	public Integer getWjtmScore() {
		return wjtmScore;
	}

	public void setWjtmScore(Integer wjtmScore) {
		this.wjtmScore = wjtmScore;
	}

	public List<Tmxx> getTmxxs() {
		return tmxxs;
	}

	public void setTmxxs(List<Tmxx> tmxxs) {
		this.tmxxs = tmxxs;
	}

	@Override
	public String toString() {
		return "Wjtm [wjtmId=" + wjtmId + ", wj=" + wj + ", wjId=" + wjId + ", wjtmType=" + wjtmType + ", wjtmTitle="
				+ wjtmTitle + ", wjtmMemo=" + wjtmMemo + ", wjtmRequired=" + wjtmRequired + ", wjtmScore=" + wjtmScore
				+ ", tmxxs=" + tmxxs + "]";
	}

    

}
