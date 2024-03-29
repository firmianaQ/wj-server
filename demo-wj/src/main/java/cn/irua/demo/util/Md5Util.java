package cn.irua.demo.util;

import java.security.MessageDigest;

public class Md5Util {
	  /**
     * 将字节数组转为十六进制数
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            //每个字节转为十六进制数后进行拼接
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将某一个字节转为十六进制数
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回大写MD5
     *
     * @param origin 要加密的原字符串
     * @param charsetname 加密算法使用的字符集
     * @return
     */
    private static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            //初始化md5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            //md.digest(resultString.getBytes())获取数据的信息摘要，返回字节数组
            //byteArrayToHexString()将字节数组转为十六进制数
            if (charsetname == null || "".equals(charsetname)) {
                //如果不传入字符集，则调用默认字符集
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
            else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception exception) {
        }
        return resultString.toUpperCase();
    }

    /**
     * 唯一public方法对外公开
     * @param origin
     * @return
     */
    public static String MD5EncodeUtf8(String origin,String salt) {
        //对原字符串加盐值返回
        origin = origin + salt;
        //传入utf-8字符集
        return MD5Encode(origin, "utf-8");
    }

    //十六进制数组值
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    /*@Test
    public void newus() {
    	String upwd = MD5EncodeUtf8("123456","admin");
    	System.out.println(upwd);
    }*/
    
  
}