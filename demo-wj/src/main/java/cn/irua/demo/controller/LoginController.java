package cn.irua.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.irua.demo.entity.Users;
import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.jsonResult.StatusCode;
import cn.irua.demo.service.TokenService;
import cn.irua.demo.service.impl.UsersServiceImpl;
import cn.irua.demo.util.Md5Util;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2019-04-26
 */
@RestController
@RequestMapping("/api")
@Scope("prototype")
@CrossOrigin
public class LoginController extends BaseController {
	@Resource
	private UsersServiceImpl usersServiceImpl;
	@Resource
	private TokenService tokenService;

	@PostMapping("/login")
	public JsonResult login(@RequestBody Users u) {
		String upwd = Md5Util.MD5EncodeUtf8(u.getUpwd(), u.getUcode());
		u.setUpwd(upwd);
		u = usersServiceImpl.findByUser(u);
		if (u != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			String userAgent = request.getHeader("user-agent");
			String token = this.tokenService.generateToken(u.getUcode(), userAgent);
			this.tokenService.save(token, u);
			map.put("token", token);
			map.put("data", u);
			jsonResult.succ(200, map);

		} else {
			jsonResult.failure(500);
		}
		return jsonResult;
	}

	@PostMapping("/outlogin")
	public JsonResult outLogin() {
		String token = request.getHeader("token");
		this.tokenService.remove(token);
		jsonResult.succ(200);
		return jsonResult;
	}

	@PostMapping("/verifyUcode")
	public JsonResult verifyUcode(@RequestBody Users u) {
		Users us = new Users();
		if (u.getUcode() != null) {
			QueryWrapper<Users> q = new QueryWrapper<Users>();
			q.eq("ucode", u.getUcode());
			us = usersServiceImpl.getOne(q);
		} else {
			QueryWrapper<Users> q = new QueryWrapper<Users>();
			q.eq("email", u.getEmail());
			us = usersServiceImpl.getOne(q);
		}

		if (us == null) {
			jsonResult.succ(200);
		} else {
			jsonResult.failure(500);
		}
		return jsonResult;
	}

	@PutMapping("/register")
	public JsonResult register(@RequestBody Users u) {
		Users us = usersServiceImpl.findByUcode(u.getUcode());
		if (us == null) {
			String upwd = Md5Util.MD5EncodeUtf8(u.getUpwd(), u.getUcode());
			u.setUpwd(upwd);
			boolean f = usersServiceImpl.save(u);
			if (f) {
				jsonResult.succ(200);
			} else {
				jsonResult.failure(500);
			}
		} else {
			jsonResult.failure(400, "用户已存在", null);
		}

		return jsonResult;
	}
}
