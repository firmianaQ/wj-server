package cn.irua.demo.service.impl;

import cn.irua.demo.entity.Users;
import cn.irua.demo.mapper.UsersMapper;
import cn.irua.demo.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyh
 * @since 2019-04-26
 */
@Service
@Scope("prototype")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

	@Override
	public Users findByUser(Users u) {
		u = baseMapper.findByUser(u);
		return u;
	}

	@Override
	public Users findByUcode(String ucode) {
		return baseMapper.findByUcode(ucode);
	}


}
