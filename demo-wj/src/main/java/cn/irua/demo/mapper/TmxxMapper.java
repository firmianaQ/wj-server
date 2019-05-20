package cn.irua.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.irua.demo.entity.Tmxx;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface TmxxMapper extends BaseMapper<Tmxx> {
	@Select("select * from tmxx x where x.wjtm_id = #{wjtm_id}")
	public List<Tmxx> getByWjtm(@Param("wjtm_id") long wjtm_id);
}
