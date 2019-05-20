package cn.irua.demo.mapper;

import cn.irua.demo.entity.Users;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2019-04-26
 */
public interface UsersMapper extends BaseMapper<Users> {
	@Select("select * from users u where u.ucode = #{ucode} and u.upwd = #{upwd}")
	public Users findByUser(Users u );
	@Select("select * from users u where u.ucode = #{ucode}")
	public Users findByUcode(String ucode);
}
