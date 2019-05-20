package cn.irua.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;

import cn.irua.demo.entity.Users;
import cn.irua.demo.jsonResult.JsonResult;
import cn.irua.demo.service.TokenService;
import cn.irua.demo.service.impl.UsersServiceImpl;
import cn.irua.demo.util.Md5Util;

@RestController
@RequestMapping("/api/login_callback")
@Scope("prototype")
@CrossOrigin
public class OtherLogin extends BaseController {
	@Resource
	private UsersServiceImpl usersServiceImpl;
	@Resource
	private TokenService tokenService;

	// 授权成功后的回调,我们需要在这个方法中拿到code去请求token
	@RequestMapping("/git")
	public JsonResult gitCode(String code, String state) {
		try {
			if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(state)) {
				// 拿到我们的code,去请求token
				// 发送一个请求到
				String token_url = GitHubConstants.TOKEN_URL.replace("CLIENT_ID", GitHubConstants.CLIENT_ID)
						.replace("CLIENT_SECRET", GitHubConstants.CLIENT_SECRET)
						.replace("CALLBACK", GitHubConstants.CALLBACK).replace("CODE", code);
				// System.out.println("用户信息数据"+token_url);//这个里面有我们想要的用户信息数据
				String responseStr = HttpClientUtils.doGet(token_url);
				String token = HttpClientUtils.parseResponseEntity(responseStr).get("access_token");
				// 根据token发送请求获取登录人的信息
				String userinfo_url = GitHubConstants.USER_INFO_URL.replace("TOKEN", token);
				responseStr = HttpClientUtils.doGet(userinfo_url);// json
				String id = responseStr.split(",")[1].split(":")[1];
				Map<String, String> responseMap = HttpClientUtils.parseResponseEntityJSON(responseStr);
				responseMap.put("id", id);
				Users us = usersServiceImpl.findByUcode(id);
				if (us == null) {
					us = new Users();
					us.setUcode(responseMap.get("id"));
					String upwd = Md5Util.MD5EncodeUtf8("123456", us.getUcode());
					us.setUpwd(upwd);
					usersServiceImpl.save(us);
				}
				String userAgent = request.getHeader("user-agent");
				String atoken = this.tokenService.generateToken(us.getUcode(), userAgent);
				this.tokenService.save(token, us);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("token", token);
				map.put("data", us);
				jsonResult.succ(200, map);
			} else {
				jsonResult.failure(500, "登录失败", null);
			}
		} catch (Exception e) {
			jsonResult.failure(500, "登录失败", null);
		}
		return jsonResult;
	}
}


class GitHubConstants {
	public static final String CLIENT_ID = "0a743bb1463b1af5435b";
	public static final String CLIENT_SECRET = "144daeb7f2cccf0247c7bca9da360d9289f54b44"; 
	public static final String CALLBACK = "http://127.0.0.1:8081/github"; // 回调地址
	// 获取code的url
	public static final String CODE_URL = "https://github.com/login/oauth/authorize?client_id=CLIENT_ID&state=STATE&redirect_uri=CALLBACK";
	// 获取token的url
	public static final String TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=CLIENT_ID&client_secret=CLIENT_SECRET&code=CODE&redirect_uri=CALLBACK";
	// 获取用户信息的url
	public static final String USER_INFO_URL = "https://api.github.com/user?access_token=TOKEN";
}

// 工具类
class HttpClientUtils {
	/**
	 * 使用HttpClient发送一个Get方式的请求
	 * 
	 * @param url
	 *            请求的路径 请求参数拼接到url后面
	 * @return 响应的数据
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet); // 发送一个http请求
		// 如果响应成功,解析响应结果
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity responseEntity = response.getEntity(); // 获取响应的内容
			return EntityUtils.toString(responseEntity);
		}
		return null;
	}

	// 参数的封装
	public static Map<String, String> parseResponseEntity(String responseEntityStr) {
		Map<String, String> map = new HashMap<>();
		String[] strs = responseEntityStr.split("\\&");
		for (String str : strs) {
			String[] mapStrs = str.split("=");
			String value = null;
			String key = mapStrs[0];
			if (mapStrs.length > 1) {
				value = mapStrs[1];
			}
			map.put(key, value);
		}
		return map;
	}

	// json字符串转map
	public static Map<String, String> parseResponseEntityJSON(String responseEntityStr) {
		Map<String, String> map = new HashMap<>();
		GsonBuilder gb = new GsonBuilder();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		Gson gson = gb.create();
		map = gson.fromJson(responseEntityStr, Map.class);
		return map;
	}

}
