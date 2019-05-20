package cn.irua.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.irua.demo.entity.Dj;
import cn.irua.demo.entity.Djtm;
import cn.irua.demo.entity.Tmxx;
import cn.irua.demo.entity.Wjtm;
import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.service.impl.DjServiceImpl;
import cn.irua.demo.service.impl.DjtmServiceImpl;
import cn.irua.demo.service.impl.TmxxServiceImpl;
import cn.irua.demo.service.impl.UsersServiceImpl;
import cn.irua.demo.service.impl.WjServiceImpl;
import cn.irua.demo.service.impl.WjtmServiceImpl;
import cn.irua.demo.util.IpUtil;
import sun.net.util.IPAddressUtil;
import sun.security.x509.IPAddressName;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Scope("prototype")
public class DjController extends BaseController {


	@Resource
	private DjServiceImpl djServiceImpl;

	@Resource
	private DjtmServiceImpl djtmServiceImpl;

	@Resource
	private WjtmServiceImpl wjtmServiceImpl;

	@Resource
	private WjServiceImpl wjServiceImpl;
	
	@PutMapping("/dj")
	public JsonResult newDj(@RequestBody Map m) {
		try {
			Long wjId = Long.valueOf((String)m.get("wjId"));
			if ("停止回收".equals(wjServiceImpl.getById(wjId).getWjState())) {
				jsonResult.succ(200,"问卷已经停止回收");
			} else {
				List<Map> djtms = (List<Map>) m.get("djtms");
				Dj dj = new Dj();
				dj.setWjId(wjId);
				dj.setDjIp(IpUtil.getIpAddr(request));
				dj.setDjAddress(IpUtil.getIPbelongAddress(dj.getDjIp()));
				dj.setDjTime(new Timestamp(new Date().getTime()));
				djServiceImpl.save(dj);
				Long djId = dj.getDjId();
				for (Map map : djtms) {
					Long wjtmId = Long.valueOf((String)map.get("wjtmId"));
					Wjtm wjtm = wjtmServiceImpl.getById(wjtmId);
					String answer = map.get("djtmAnswer").toString().replace("[", "").replace("]","").replace(" ","");
					Djtm djtm = new Djtm();
					djtm.setDjId(djId);
					djtm.setWjtmId(wjtmId);
					if ("单选".equals(wjtm.getWjtmType())||"多选".equals(wjtm.getWjtmType())) {
						djtm.setDjtmAnswer(answer);
					} else {
						djtm.setDjtmAnswerText(answer);
					}
					djtmServiceImpl.save(djtm);
				}
				jsonResult.succ(200,dj);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.getMessage(), null);
		}
		return jsonResult;
	}
}
