package cn.irua.demo.service.impl;

import cn.irua.demo.entity.Djtm;
import cn.irua.demo.mapper.DjtmMapper;
import cn.irua.demo.service.IDjtmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
public class DjtmServiceImpl extends ServiceImpl<DjtmMapper, Djtm> implements IDjtmService {

	@Override
	public int getCheckBoxAnswerCount(String text) {
		// TODO Auto-generated method stub
		return baseMapper.getCheckBoxAnswerCount(text);
	}

	@Override
	public int getRadioAnswerCount(String text) {
		// TODO Auto-generated method stub
		return baseMapper.getRadioAnswerCount(text);
	}

	

}
