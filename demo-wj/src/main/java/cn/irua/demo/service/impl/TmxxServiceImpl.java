package cn.irua.demo.service.impl;

import cn.irua.demo.entity.Tmxx;
import cn.irua.demo.mapper.TmxxMapper;
import cn.irua.demo.service.ITmxxService;
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
public class TmxxServiceImpl extends ServiceImpl<TmxxMapper, Tmxx> implements ITmxxService {

	@Override
	public List<Tmxx> getByWjtm(Long wjtmId) {
		// TODO Auto-generated method stub
		return baseMapper.getByWjtm(wjtmId);
	}



}
