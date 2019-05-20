package cn.irua.demo.service;

import cn.irua.demo.entity.Wjtm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface IWjtmService extends IService<Wjtm> {
	public List<Wjtm> getByWj(long wjId);
}
