package cn.irua.demo.service.impl;

import cn.irua.demo.entity.Wjtm;
import cn.irua.demo.mapper.WjtmMapper;
import cn.irua.demo.service.IWjtmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
public class WjtmServiceImpl extends ServiceImpl<WjtmMapper, Wjtm> implements IWjtmService {

	@Override
	public List<Wjtm> getByWj(long wjId) {
		// TODO Auto-generated method stub
		return baseMapper.getByWj(wjId);
	}

}
