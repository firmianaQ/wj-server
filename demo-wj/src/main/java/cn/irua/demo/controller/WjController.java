package cn.irua.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.irua.demo.entity.Tmxx;
import cn.irua.demo.entity.Wj;
import cn.irua.demo.entity.Wjtm;
import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.service.TokenService;
import cn.irua.demo.service.impl.TmxxServiceImpl;
import cn.irua.demo.service.impl.UsersServiceImpl;
import cn.irua.demo.service.impl.WjServiceImpl;
import cn.irua.demo.service.impl.WjtmServiceImpl;
import cn.irua.demo.util.AuthToken;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Scope("prototype")
@AuthToken
public class WjController extends BaseController {
	@Resource
	private UsersServiceImpl usersServiceImpl;

	@Resource
	private WjServiceImpl wjServiceImpl;

	@Resource
	private WjtmServiceImpl wjtmServiceImpl;

	@Resource
	private TmxxServiceImpl tmxxServiceImpl;

	@Resource
	private TokenService tokenService;

	private final String ACCESSTOKEN = "token";

	@GetMapping("/data/wjs/{current}/{uid}")
	public JsonResult getWjDatas(@PathVariable("current") Integer current, @PathVariable("uid") Long uid) {
		Page<Wj> page = new Page<Wj>();
		page.setCurrent(current);
		page.setSize(6);
		page = (Page<Wj>) wjServiceImpl.selectWjPage(page, uid);
		jsonResult.succ(200, page);
		return jsonResult;
	}

	@GetMapping("/data/wjs/{wjid}")
	public JsonResult getWjAllDatas(@PathVariable("wjid") Long wjid) {
		try {
			Wj wj = wjServiceImpl.getById(wjid);
			List<Wjtm> wjtms = wjtmServiceImpl.getByWj(wjid);
			wj.setWjtms(wjtms);
			if ("停止回收".equals(wj.getWjState())) {
				jsonResult.succ(200,"问卷已经停止回收",wj);
			} else {
				jsonResult.succ(200, wj);
			}
			
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}

		return jsonResult;
	}

	@PutMapping("/wj")
	public JsonResult newWj(@RequestBody Map m) {
		try {
			Wj wj = new Wj();
			Map u = (Map) m.get("user");
			List<Map> qs = (List<Map>) m.get("question");
			wj.setUid(((Integer) u.get("uid")).longValue());
			wj.setWjTitle((String) m.get("wjTitle"));
			wj.setWjMemo((String) m.get("wjMemo"));
			wj.setWjType((String) m.get("wjType"));
			wj.setWjState((String) m.get("wjState"));
			wj.setWjInitiator((String) m.get("wjInitiator"));
			wjServiceImpl.save(wj);
			Long wjId = wj.getWjId();
			for (Map map : qs) {
				map.remove("edit");
				Wjtm wjtm = new Wjtm();
				wjtm.setWjId(wjId);
				String type = (String) map.get("type");
				wjtm.setWjtmTitle((String) map.get("title"));
				wjtm.setWjtmMemo((String) map.get("memo") + "");
				boolean b = (boolean) map.get("required");
				String required = String.valueOf(b);
				wjtm.setWjtmRequired(required);
				wjtm.setWjtmScore((Integer) map.get("score"));
				wjtm.setWjtmType(type);
				wjtmServiceImpl.save(wjtm);
				if (type.equals("单选") || type.equals("多选")) {
					List<Map> ts = (ArrayList<Map>) map.get("tmxx");
					List<Tmxx> tmxxs = new ArrayList<Tmxx>();
					for (Map xx : ts) {
						Tmxx tmxx = new Tmxx();
						tmxx.setWjtmId(wjtm.getWjtmId());
						tmxx.setTmxxTitle((String) xx.get("tmxxTitle"));
						if (xx.get("tmxxSortkey") != null) {
							tmxx.setTmxxSortkey(Long.valueOf((String) xx.get("tmxxSortkey")));
						}
						tmxx.setTmxxIscorrectchoice((String) xx.get("tmxxIscorrectchoice"));
						tmxxs.add(tmxx);
					}
					tmxxServiceImpl.saveBatch(tmxxs);
				}

			}
		} catch (Exception e) {
			jsonResult.failure(500, e.getMessage(), null);
		}
		jsonResult.succ(200);
		return jsonResult;
	}

	@PutMapping("/wj/{wjid}")
	public JsonResult copyWj(@PathVariable("wjid") Long wjid) {
		try {
			Wj wj = wjServiceImpl.getById(wjid);
			wj.setWjId(null);
			wj.setWjState(null);
			wjServiceImpl.save(wj);
			Long newWjid = wj.getWjId();
			List<Wjtm> tms = wjtmServiceImpl.getByWj(wjid);
			for (Wjtm wjtm : tms) {
				Long oldWjtmId = wjtm.getWjtmId();
				wjtm.setWjtmId(null);
				wjtm.setWjId(newWjid);
				wjtmServiceImpl.save(wjtm);
				String type = wjtm.getWjtmType();
				if (type.equals("单选") || type.equals("多选")) {
					List<Tmxx> xxs = tmxxServiceImpl.getByWjtm(oldWjtmId);
					List<Tmxx> tmxxs = new ArrayList<Tmxx>();
					for (Tmxx tmxx : xxs) {
						tmxx.setTmxxId(null);
						tmxx.setWjtmId(wjtm.getWjtmId());
						tmxxs.add(tmxx);
					}
					tmxxServiceImpl.saveBatch(tmxxs);
				}
			}
			jsonResult.succ(200);
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}

		return jsonResult;
	}

	@PatchMapping("/wj/{wjid}")
	public JsonResult updateWj(@PathVariable("wjid") Long wjid, @RequestBody Wj wj) {
		try {
			boolean f = wjServiceImpl.updateById(wj);
			if (f) {
				jsonResult.succ(200);
			} else {
				jsonResult.failure(400);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}

	@DeleteMapping("wj/{wjid}")
	public JsonResult delWj(@PathVariable("wjid") Long wjid) {
		try {
			boolean f = wjServiceImpl.removeById(wjid);
			if (f) {
				jsonResult.succ(200);
			} else {
				jsonResult.failure(404);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}

	@PatchMapping("/wjtm/{wjtmid}")
	public JsonResult updateWjtm(@PathVariable("wjtmid") Long wjtmid, @RequestBody Wjtm wjtm) {
		try {
			boolean f = wjtmServiceImpl.updateById(wjtm);

			if (f) {
				if ("多选".equals(wjtm.getWjtmType()) || "单选".equals(wjtm.getWjtmType())) {
					List<Tmxx> tmxxs = wjtm.getTmxxs();
					for (Tmxx tmxx : tmxxs) {
						tmxx.setWjtmId(wjtmid);
					}
					f = tmxxServiceImpl.saveOrUpdateBatch(tmxxs);
					if (f) {
						jsonResult.succ(200);
					}
				}else {
					jsonResult.succ(200);
				}
			} else {
				jsonResult.failure(400);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}

	@PutMapping("/wjtm")
	public JsonResult newWjtm(@RequestBody Wjtm wjtm) {
		try {
			wjtm.setWjtmId(null);
			boolean f = wjtmServiceImpl.save(wjtm);
			if (f) {
				String type = wjtm.getWjtmType();
				if (type.equals("单选") || type.equals("多选")) {
					List<Tmxx> tmxxs = wjtm.getTmxxs();
					for (Tmxx tmxx : tmxxs) {
						tmxx.setTmxxId(null);
						tmxx.setWjtmId(wjtm.getWjtmId());
					}
					f = tmxxServiceImpl.saveBatch(tmxxs);
					if (f) {
						jsonResult.succ(200);
					}
				} else {
					jsonResult.succ(200);
				}

			} else {
				jsonResult.failure(400);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}
	@DeleteMapping("wjtm/{wjtmid}")
	public JsonResult delWjtm(@PathVariable("wjtmid") Long wjtmid) {
		try {
			boolean f = wjtmServiceImpl.removeById(wjtmid);
			if (f) {
				jsonResult.succ(200);
			} else {
				jsonResult.failure(404);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}
	@DeleteMapping("wjtm/tmxx/{tmxxId}")
	public JsonResult delTmxx(@PathVariable("tmxxId") Long tmxxId) {
		try {
			boolean f = tmxxServiceImpl.removeById(tmxxId);
			if (f) {
				jsonResult.succ(200);
			} else {
				jsonResult.failure(404);
			}
		} catch (Exception e) {
			jsonResult.failure(500, e.toString(), e);
		}
		return jsonResult;
	}

}
