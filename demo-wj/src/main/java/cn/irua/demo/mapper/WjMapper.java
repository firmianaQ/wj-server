package cn.irua.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.irua.demo.entity.Users;
import cn.irua.demo.entity.Wj;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface WjMapper extends BaseMapper<Wj> {
	@Select("select * from wj w where w.uid = #{uid}")
	IPage<Wj> selectWjPage(Page<Wj> page,@Param("uid") long uid);
}
