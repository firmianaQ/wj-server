package cn.irua.demo.mapper;

import cn.irua.demo.entity.Djtm;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface DjtmMapper extends BaseMapper<Djtm> {
	@Select("select count(*) from djtm x where x.djtm_answer like '%#{text}%'")
	public int getCheckBoxAnswerCount(@Param("text") String text);
	@Select("select count(*) from djtm x where x.djtm_answer = #{text}")
	public int getRadioAnswerCount(@Param("text") String text);
}
