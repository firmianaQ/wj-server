package cn.irua.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	/**
	 * 发送邮箱验证码
	 * @param strMail  接受邮件的账户数组
	 * @param strTitle 邮件标题
	 * @param strText  邮件内容
	 * @return code=0 发送成功
	 */
	public static Map<String,Object> send_qqmail(String[] strMail,String verifyEmail){  
		Map<String,Object> map = new HashMap<String,Object>();
        try  
        {  
            final Properties props = new Properties();  
            //将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
            props.put("mail.smtp.auth", "true"); 
            //设置 使用QQ邮箱发送邮件的主机名
            props.put("mail.smtp.host", "smtp.qq.com");  
            //你自己的邮箱  
             
            props.put("mail.user", "naivewtfission@foxmail.com"); 
            //你开启pop3/smtp时的授权码
           
            props.put("mail.password", "wadwswdirqpdbcjb"); 
            //smtp的端口号
            props.put("mail.smtp.port", "465");
            //始终使用安全设置
            props.put("mail.smtp.starttls.enable", "true");  
            //使用ssl协议来保证连接安全
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
            //传输超时时间
            props.put("mail.smtp.timeout", "25000");
            
            //创建一个连接的用户对象  匿名内部类                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
            Authenticator authenticator = new Authenticator() {  
                protected PasswordAuthentication getPasswordAuthentication() {  
                    String userName = props.getProperty("mail.user");  
                    String password = props.getProperty("mail.password");  
                    return new PasswordAuthentication(userName, password);  
                }  
            };  
            // 使用环境属性和授权信息，创建邮件会话  
            Session mailSession = Session.getInstance(props, authenticator);  
            // 创建邮件消息  
            MimeMessage message = new MimeMessage(mailSession);  
            // 设置发件人  
            String username = props.getProperty("mail.user");  
            InternetAddress form = new InternetAddress(username,"【irua问卷】");  
            message.setFrom(form);  
            
            
            //解析接收人邮箱数组strMail，转化为InternetAddress类型数组
            InternetAddress to =null;
            InternetAddress [] InternetAddressArray=new InternetAddress[strMail.length];
            for (int i=0;i<strMail.length;i++) {
            	to = new InternetAddress(strMail[i]);
            	InternetAddressArray[i]=to;
			}
            
            message.setRecipients(RecipientType.TO, InternetAddressArray);  
  
            // 设置邮件标题  
            message.setSubject("您正在验证您的邮箱");  
  
            // 设置邮件的内容体  
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("<div id=\"login_form\" style=\"position: relative;z-index: 1;background: #FFFFFF;max-width: 360px;margin: 0 auto;padding: 45px;text-align: center;box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);\"><div>验证码是："+verifyEmail);
            strBuilder.append("</div><div>如果这不是您本人的操作，请忽略本邮件</div></div>");
            message.setContent(strBuilder.toString(), "text/html;charset=UTF-8");  
  
            // 发送邮件  
            Transport.send(message);  
            map.put("code",0);
            map.put("msg","验证码已发送");
        }  
        catch (AddressException e) {  
             
             map.put("code",500);
             map.put("msg",e);
        }  
        catch (MessagingException e) {  
             e.printStackTrace();  
             map.put("msg",e);
        }  
        catch (Exception e){  
            e.printStackTrace(); 
            map.put("msg",e);
        }  
        return map;  
    }  
	 
}
