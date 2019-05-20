package cn.irua.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class Dj implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dj_id", type = IdType.AUTO)
	private Long djId;
	@TableField(exist = false)
	private Wj wj;
	private Long wjId;
	/**
	 * 答题人
	 */
	private String djPerson;
	/**
	 * 答卷ip
	 */
	private String djIp;
	/**
	 * 答卷地址
	 */
	private String djAddress;
	/**
	 * 答题时间
	 */
	private Date djTime;
	@TableField(exist = false)
	private List<Djtm> djtms = new ArrayList<Djtm>();

	public Long getDjId() {
		return djId;
	}

	public void setDjId(Long djId) {
		this.djId = djId;
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

	public String getDjPerson() {
		return djPerson;
	}

	public String getDjIp() {
		return djIp;
	}

	public void setDjIp(String djIp) {
		this.djIp = djIp;
	}

	public void setDjPerson(String djPerson) {
		this.djPerson = djPerson;
	}

	public String getDjAddress() {
		return djAddress;
	}

	public void setDjAddress(String djAddress) {
		this.djAddress = djAddress;
	}

	public Date getDjTime() {
		return djTime;
	}

	public void setDjTime(Date djTime) {
		this.djTime = djTime;
	}

	public List<Djtm> getDjtms() {
		return djtms;
	}

	public void setDjtms(List<Djtm> djtms) {
		this.djtms = djtms;
	}

	@Override
	public String toString() {
		return "Dj [djId=" + djId + ", wj=" + wj + ", wjId=" + wjId + ", djPerson=" + djPerson + ", djIp=" + djIp
				+ ", djAddress=" + djAddress + ", djTime=" + djTime + ", djtms=" + djtms + "]";
	}
}
