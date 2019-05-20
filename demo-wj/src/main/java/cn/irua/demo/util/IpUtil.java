package cn.irua.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

public class IpUtil {

	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	private static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取ip 和 地址
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getIpBelongAddress(HttpServletRequest request) {

		String ip = getIpAddr(request);
		String address = getIPbelongAddress(ip);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ip",ip);
		m.put("address", address);
		return m;
	}

	/**
	 * 根据ip获取地址
	 * @param ip
	 * @return
	 */
	public static String getIPbelongAddress(String ip) {

		String ipAddress = "[]";
		try {
			Gson gson = new Gson();
			String context = call("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			Map<String, Object> m = new HashMap<String, Object>();
			m = gson.fromJson(context, Map.class);
			int code = (int) Math.ceil((double) m.get("code"));
			if (code == 0) {
				m= (Map<String, Object>) m.get("data");
				ipAddress = (String) m.get("country") + "-" + (String) m.get("region") + "-" + (String) m.get("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipAddress;
	}
	
	private static String call(String urlStr) {

		try {

			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("GET");

			int code = httpCon.getResponseCode();

			if (code == 200) {
				return streamConvertToSting(httpCon.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String streamConvertToSting(InputStream is) {

		String tempStr = "";
		try {

			if (is == null)
				return null;
			ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = is.read(by)) != -1) {
				arrayOut.write(by, 0, len);
			}
			tempStr = new String(arrayOut.toByteArray());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempStr;
	}

}