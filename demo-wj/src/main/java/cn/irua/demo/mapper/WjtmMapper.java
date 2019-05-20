package cn.irua.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.irua.demo.entity.Wjtm;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface WjtmMapper extends BaseMapper<Wjtm> {

	public List<Wjtm> getByWj(@Param("wjId")long wjId);
}
