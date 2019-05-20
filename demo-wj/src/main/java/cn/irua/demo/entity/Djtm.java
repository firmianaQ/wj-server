package cn.irua.demo.entity;

import java.io.Serializable;

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
public class Djtm implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "djtm_id", type = IdType.AUTO)
	private Long djtmId;
	@TableField(exist = false)
	private Dj dj;

	private Long djId;
	@TableField(exist = false)
	private Wjtm wjtm;
	private Long wjtmId;
	private String djtmAnswer;

	private String djtmAnswerText;

	private String djtmScore;

	public Long getDjtmId() {
		return djtmId;
	}

	public void setDjtmId(Long djtmId) {
		this.djtmId = djtmId;
	}

	public Dj getDj() {
		return dj;
	}

	public void setDj(Dj dj) {
		this.dj = dj;
	}

	public Long getDjId() {
		return djId;
	}

	public void setDjId(Long djId) {
		this.djId = djId;
	}

	public Wjtm getWjtm() {
		return wjtm;
	}

	public void setWjtm(Wjtm wjtm) {
		this.wjtm = wjtm;
	}

	public Long getWjtmId() {
		return wjtmId;
	}

	public void setWjtmId(Long wjtmId) {
		this.wjtmId = wjtmId;
	}

	public String getDjtmAnswer() {
		return djtmAnswer;
	}

	public void setDjtmAnswer(String djtmAnswer) {
		this.djtmAnswer = djtmAnswer;
	}

	public String getDjtmAnswerText() {
		return djtmAnswerText;
	}

	public void setDjtmAnswerText(String djtmAnswerText) {
		this.djtmAnswerText = djtmAnswerText;
	}

	public String getDjtmScore() {
		return djtmScore;
	}

	public void setDjtmScore(String djtmScore) {
		this.djtmScore = djtmScore;
	}

	@Override
	public String toString() {
		return "Djtm [djtmId=" + djtmId + ", dj=" + dj + ", djId=" + djId + ", wjtm=" + wjtm + ", wjtmId=" + wjtmId
				+ ", djtmAnswer=" + djtmAnswer + ", djtmAnswerText=" + djtmAnswerText + ", djtmScore=" + djtmScore
				+ "]";
	}

}
