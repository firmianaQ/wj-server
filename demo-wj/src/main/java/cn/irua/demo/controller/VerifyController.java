package cn.irua.demo.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.util.EmailUtil;
import cn.irua.demo.util.RandomCode;
import cn.irua.demo.util.VerifyCodeUtils;

@RestController
@RequestMapping("/api/vcode")
@Scope("prototype")
@CrossOrigin
public class VerifyController extends BaseController {

	// 生成图片验证码
	@GetMapping(value = "/imgcode")
	public void getVerifyCode() {
		try {
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
			session.setAttribute("verifyCode", verifyCode);
			OutputStream os = response.getOutputStream();
			VerifyCodeUtils.outputImage(100, 40, os, verifyCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 验证图片验证码
	@PostMapping(value = "/imgcode/icode")
	@ResponseBody
	public JsonResult verifyCode(@RequestBody Map m) {
		String vCode = (String) session.getAttribute("verifyCode");
		String vcode = (String) m.get("vcode");
		if (vCode.equalsIgnoreCase(vcode)) {
			jsonResult.succ(200);
			session.removeAttribute("verifyCode");
			return jsonResult;
		} else {
			jsonResult.failure(500);
			return jsonResult;
		}
	}

	// 发送邮箱验证码
	@PostMapping(value = "/emailcode")
	@ResponseBody
	public JsonResult getVerifyEmail(@RequestBody Map m) {
		List<String> emailList = (List<String>) m.get("emails");
		String[] emails = new String[emailList.size()];
		for (int i = 0; i < emailList.size(); i++) {
			emails[i] = emailList.get(i);
		}
		// 初始化clnt,使用单例方式
		int verifyEmail = RandomCode.randomCode(4);
		session.setAttribute("verifyEmail", verifyEmail);
		jsonResult.succ(200, EmailUtil.send_qqmail(emails, verifyEmail + ""));
		return jsonResult;
	}

	// 验证邮箱验证码
	@PostMapping(value = "/emailcode/ecode")
	@ResponseBody
	public JsonResult verifyEmail(@RequestBody Map m) {
		if (session.getAttribute("verifyEmail") == null) {
			jsonResult.failure(500,"验证码过期", null);
		}else {
			int vEmail = (int) session.getAttribute("verifyEmail");
			String verifyEmail = (String) m.get("ecode");
			if ((vEmail + "").equalsIgnoreCase(verifyEmail)) {
				jsonResult.succ(200);
				session.removeAttribute("verifyEmail");
			} else {
				jsonResult.failure(500,"验证码错误", null);
			}
		}
		
		return jsonResult;
	}
}
