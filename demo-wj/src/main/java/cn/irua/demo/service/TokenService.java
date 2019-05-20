package cn.irua.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cn.irua.demo.entity.Users;
import cn.irua.demo.util.Md5Util;
import cn.irua.demo.util.RedisUtil;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

@Service
public class TokenService {

	@Resource
	private RedisUtil redisUtil;

	// 生成token(格式为token:设备-加密的用户名-时间-六位随机数)
	public String generateToken(String ucode,String userAgentStr) {
		StringBuilder token = new StringBuilder("token:");
		 //设备
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        if (userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE)) {
            token.append("MOBILE-");
        } else {
            token.append("PC-");
        }
        // 加密的用户名
		token.append(Md5Util.MD5EncodeUtf8(ucode, "") + "-");
		// 时间
		token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
		// 六位随机字符串
		token.append(new Random().nextInt(999999 - 111111 + 1) + 111111);
		System.out.println("token-->" + token.toString());
		return token.toString();
	}

	// 把token存到redis中
	public void save(String token, Users user) {
		Gson gson = new Gson();
		redisUtil.setex(token, gson.toJson(user), 1);
	}
	//主动删除token
	public Boolean remove(String token) {
		return redisUtil.remove(token);
	}

	public Users get(String token) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Users u = gson.fromJson(redisUtil.get(token), Users.class);
		return u;
	}

	
}
