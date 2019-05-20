package cn.irua.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class Tmxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tmxx_id", type = IdType.AUTO)
    private Long tmxxId;
    @TableField(exist=false)
    private Wjtm wjtm;

    private Long wjtmId;
    
    private String tmxxTitle;

    private Long tmxxSortkey;

    private String tmxxIscorrectchoice;
   
	public Long getTmxxId() {
		return tmxxId;
	}

	public void setTmxxId(Long tmxxId) {
		this.tmxxId = tmxxId;
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

	public String getTmxxTitle() {
		return tmxxTitle;
	}

	public void setTmxxTitle(String tmxxTitle) {
		this.tmxxTitle = tmxxTitle;
	}

	public Long getTmxxSortkey() {
		return tmxxSortkey;
	}

	public void setTmxxSortkey(Long tmxxSortkey) {
		this.tmxxSortkey = tmxxSortkey;
	}

	public String getTmxxIscorrectchoice() {
		return tmxxIscorrectchoice;
	}

	public void setTmxxIscorrectchoice(String tmxxIscorrectchoice) {
		this.tmxxIscorrectchoice = tmxxIscorrectchoice;
	}

	

	@Override
	public String toString() {
		return "Tmxx [tmxxId=" + tmxxId + ", wjtm=" + wjtm + ", wjtmId=" + wjtmId + ", tmxxTitle=" + tmxxTitle
				+ ", tmxxSortkey=" + tmxxSortkey + ", tmxxIscorrectchoice=" + tmxxIscorrectchoice + "]";
	}


}
