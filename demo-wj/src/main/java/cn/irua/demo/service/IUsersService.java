package cn.irua.demo.service;

import cn.irua.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyh
 * @since 2019-04-26
 */
public interface IUsersService extends IService<Users> {
	public Users findByUser(Users u);
	public Users findByUcode(String ucode);
}
