package cn.irua.demo.service.impl;

import cn.irua.demo.entity.Dj;
import cn.irua.demo.mapper.DjMapper;
import cn.irua.demo.service.IDjService;
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
public class DjServiceImpl extends ServiceImpl<DjMapper, Dj> implements IDjService {

}
