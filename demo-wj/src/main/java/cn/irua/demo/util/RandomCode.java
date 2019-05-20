package cn.irua.demo.util;

public class RandomCode {
	/**
	 * 
	 * @param i 验证码是几位数 最少4位，最多6位
	 * @return
	 */
	public static Integer randomCode(int i) {
		if (i < 4) {
			i = 4;
		} else if (i > 6) {
			i = 6;
		}
		String n = "1";
		for (int j = 1; j < i; j++) {
			n = n + "0";
		}
		int num = Integer.parseInt(n);
		int randomCode = (int) ((Math.random() * 9 + 1) * num);
		return randomCode;
	}

	

}
