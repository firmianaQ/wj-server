package cn.irua.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;

import cn.irua.demo.entity.Dj;
import cn.irua.demo.entity.Djtm;
import cn.irua.demo.entity.Stat_Info;
import cn.irua.demo.entity.Tmxx;
import cn.irua.demo.entity.Users;
import cn.irua.demo.entity.Wj;
import cn.irua.demo.entity.Wjtm;
import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.service.TokenService;
import cn.irua.demo.service.impl.DjServiceImpl;
import cn.irua.demo.service.impl.DjtmServiceImpl;
import cn.irua.demo.service.impl.TmxxServiceImpl;
import cn.irua.demo.service.impl.WjServiceImpl;
import cn.irua.demo.service.impl.WjtmServiceImpl;
import cn.irua.demo.util.AuthToken;
import cn.irua.demo.util.WordUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Scope("prototype")
@AuthToken
public class DataAnalysisController extends BaseController {
	@Resource
	private DjServiceImpl djServiceImpl;

	@Resource
	private DjtmServiceImpl djtmServiceImpl;
	@Resource
	private WjServiceImpl wjServiceImpl;

	@Resource
	private WjtmServiceImpl wjtmServiceImpl;

	@Resource
	private TmxxServiceImpl tmxxServiceImpl;
	@Resource
	private TokenService tokenService;

	@GetMapping("/chart/{wjid}")
	public JsonResult dataAnalysis(@PathVariable("wjid") Long wjid) {
		try {
			Wj wj = wjServiceImpl.getById(wjid);
			Users u = tokenService.get(request.getHeader("token"));
			if (u.getUid() - wj.getUid() != 0) {
				jsonResult.failure(404, "问卷没有找到", null);
			} else {
				QueryWrapper<Dj> q1 = new QueryWrapper<>();
				q1.eq("wj_id", wjid);
				// 回收总量
				int recycle = djServiceImpl.count(q1);
				// 获取问卷数据
				List<Wjtm> wjtms = wjtmServiceImpl.getByWj(wjid);
				for (Wjtm wjtm : wjtms) {
					List<Tmxx> tmxxs = tmxxServiceImpl.getByWjtm(wjtm.getWjtmId());
					wjtm.setTmxxs(tmxxs);
					Stat_Info stat_Info = new Stat_Info();
					Map m = new HashMap<>();
					// 获取当前题目答案总数
					QueryWrapper<Djtm> q2 = new QueryWrapper<>();
					q2.eq("wjtm_id", wjtm.getWjtmId());
					m.put("answer_total", djtmServiceImpl.count(q2));
					List op = new ArrayList<>();
					if ("单选".equals(wjtm.getWjtmType())) {
						for (Tmxx tmxx : tmxxs) {
							QueryWrapper<Djtm> q3 = new QueryWrapper<>();
							q3.eq("djtm_answer", tmxx.getTmxxId());
							Map mc = new HashMap<>();
							mc.put("value", djtmServiceImpl.count(q3));
							mc.put("name", tmxx.getTmxxTitle());
							mc.put("tmxxId", tmxx.getTmxxId());
							op.add(mc);
						}
						m.put("optios", op);
					} else if ("多选".equals(wjtm.getWjtmType())) {
						for (Tmxx tmxx : tmxxs) {
							QueryWrapper<Djtm> q3 = new QueryWrapper<>();
							q3.like("djtm_answer", tmxx.getTmxxId());
							Map mc = new HashMap<>();
							mc.put("value", djtmServiceImpl.count(q3));
							mc.put("name", tmxx.getTmxxTitle());
							mc.put("tmxxId", tmxx.getTmxxId());
							op.add(mc);
						}
						m.put("optios", op);
					} else {
						List<Djtm> djtms = djtmServiceImpl.list(q2);
						List<String> texts = new ArrayList<String>();
						for (Djtm djtm : djtms) {
							texts.add(djtm.getDjtmAnswerText());
						}
						m.put("texts", texts);
					}
					stat_Info.setTotal(m);
					wjtm.setStat_info(stat_Info);

				}
				wj.setWjtms(wjtms);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("wj", wj);
				data.put("recycle", recycle);
				jsonResult.succ(200, data);
			}
		} catch (Exception e) {
			jsonResult.failure(500,e.toString(),e);
		}
		return jsonResult;
	}
	@GetMapping("/analysis/word/{wjid}")
	public void wordAnalysis (@PathVariable("wjid") Long wjid){
		try {
			Map data = getData(wjid);
			Gson gson = new Gson();
			String wjson = gson.toJson(data.get("wj"));
			Wj wj = gson.fromJson(wjson, Wj.class) ; 
			Map map = new LinkedHashMap<>();
			map.put("recycle", data.get("recycle"));
			map.put("wjTitle", wj.getWjTitle());
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		    int index = 1;
		    for (Wjtm wjtm : wj.getWjtms()) {
		    	Map m = new LinkedHashMap<>();
		    	m.put("wjtmTitle", wjtm.getWjtmTitle());
		    	m.put("wjtmType", "["+wjtm.getWjtmType()+"]");
		    	if ("单选".equals(wjtm.getWjtmType())||"多选".equals(wjtm.getWjtmType())) {
		    		m.put("tmxx",wjtm.getStat_info().getTotal().get("optios"));
				} else {
					m.put("texts", wjtm.getStat_info().getTotal().get("texts"));
				}
		    	list.add(m);
		    	index++;
			}
		    map.put("list", list);
			WordUtils.exportMillCertificateWord(request, response, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private Map getData(Long wjid) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Wj wj = wjServiceImpl.getById(wjid);
			Users u = tokenService.get(request.getHeader("token"));
			if (u.getUid() - wj.getUid() != 0) {
				jsonResult.failure(404, "问卷没有找到", null);
			} else {
				QueryWrapper<Dj> q1 = new QueryWrapper<>();
				q1.eq("wj_id", wjid);
				// 回收总量
				int recycle = djServiceImpl.count(q1);
				// 获取问卷数据
				List<Wjtm> wjtms = wjtmServiceImpl.getByWj(wjid);
				for (Wjtm wjtm : wjtms) {
					List<Tmxx> tmxxs = tmxxServiceImpl.getByWjtm(wjtm.getWjtmId());
					wjtm.setTmxxs(tmxxs);
					Stat_Info stat_Info = new Stat_Info();
					Map m = new HashMap<>();
					// 获取当前题目答案总数
					QueryWrapper<Djtm> q2 = new QueryWrapper<>();
					q2.eq("wjtm_id", wjtm.getWjtmId());
					m.put("answer_total", djtmServiceImpl.count(q2));
					List op = new ArrayList<>();
					if ("单选".equals(wjtm.getWjtmType())) {
						for (Tmxx tmxx : tmxxs) {
							QueryWrapper<Djtm> q3 = new QueryWrapper<>();
							q3.eq("djtm_answer", tmxx.getTmxxId());
							Map mc = new HashMap<>();
							mc.put("value", djtmServiceImpl.count(q3));
							mc.put("name", tmxx.getTmxxTitle());
							mc.put("tmxxId", tmxx.getTmxxId());
							op.add(mc);
						}
						m.put("optios", op);
					} else if ("多选".equals(wjtm.getWjtmType())) {
						for (Tmxx tmxx : tmxxs) {
							QueryWrapper<Djtm> q3 = new QueryWrapper<>();
							q3.like("djtm_answer", tmxx.getTmxxId());
							Map mc = new HashMap<>();
							mc.put("value", djtmServiceImpl.count(q3));
							mc.put("name", tmxx.getTmxxTitle());
							mc.put("tmxxId", tmxx.getTmxxId());
							op.add(mc);
						}
						m.put("optios", op);
					} else {
						List<Djtm> djtms = djtmServiceImpl.list(q2);
						List<String> texts = new ArrayList<String>();
						for (Djtm djtm : djtms) {
							texts.add(djtm.getDjtmAnswerText());
						}
						m.put("texts", texts);
					}
					stat_Info.setTotal(m);
					wjtm.setStat_info(stat_Info);

				}
				wj.setWjtms(wjtms);
				data.put("wj", wj);
				data.put("recycle", recycle);
			}
		} catch (Exception e) {
		}
		return data;
	}
}
