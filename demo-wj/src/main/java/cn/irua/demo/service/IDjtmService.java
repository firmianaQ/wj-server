package cn.irua.demo.service;

import cn.irua.demo.entity.Djtm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyh
 * @since 2019-04-30
 */
public interface IDjtmService extends IService<Djtm> {
	public int getCheckBoxAnswerCount(String text);
	public int getRadioAnswerCount(String text);
}
