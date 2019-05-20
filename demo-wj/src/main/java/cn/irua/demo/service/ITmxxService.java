package cn.irua.demo.service;

import cn.irua.demo.entity.Tmxx;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface ITmxxService extends IService<Tmxx> {
	public List<Tmxx> getByWjtm(Long wjtmId);
}
