package cn.irua.demo.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.irua.demo.entity.Users;
import cn.irua.demo.entity.Wj;
import cn.irua.demo.mapper.WjMapper;
import cn.irua.demo.service.IWjService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
@Service
@Scope("prototype")
public class WjServiceImpl extends ServiceImpl<WjMapper, Wj> implements IWjService {
	public IPage<Wj> selectWjPage(Page<Wj> page, long uid) {
	    return baseMapper.selectWjPage(page, uid);
	}
}
