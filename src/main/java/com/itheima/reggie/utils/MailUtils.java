package com.itheima.reggie.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
    public static void main(String[] args) throws MessagingException {
        //可以在这里直接测试方法，填自己的邮箱即可
        sendTestMail("405350634@qq.com", new MailUtils().achieveCode());
    }

    public static void sendTestMail(String email, String code) throws MessagingException {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱端口587
        props.put("mail.smtp.port", "587");
        // 此处填写，写信人的账号
        props.put("mail.user", "405350634@qq.com");
        // 此处填写16位STMP口令
        props.put("mail.password", "webouzghwvoacbbi");
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
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
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress(email);
        message.setRecipient(RecipientType.TO, to);
        // 设置邮件标题
        message.setSubject("Welcome to Reggie's Takeout Services!");
        // 设置邮件的内容体
        //message.setContent("Dear user:Hello! \n registration verification code is:" + code + "(Valid for one minute, please do not tell others)", "text/html;charset=UTF-8");
        // 最后当然就是发送邮件啦
        String content = "<html><body><p>Dear Customer,</p>"
                + "<p>Thank you for choosing Reggie Takeout Services. We are committed to providing you with:</p>"
                + "<ul><li>High-quality</li>"
                + "<li>Convenient, and</li>"
                + "<li>Secure takeout services.</li></ul>"
                + "<p>To ensure the security of your account, we have generated the following registration verification code:</p>"
                + "<p><strong>Verification Code: " + code + "</strong></p>"
                + "<p>This code will be valid for one minute, so please do not share it with anyone. If you did not request this code, please disregard this email.</p>"
                + "<p>If you have any questions or need assistance, please feel free to contact our customer service team. We are here to help you.</p>"
                + "<p>Thank you for choosing Reggie Takeout Services!</p>"
                + "<p>Best regards,<br>The Reggie Takeout Services Team</p></body></html>";

        message.setContent(content, "text/html;charset=UTF-8");

        Transport.send(message);
    }

    public static String achieveCode() {  //由于数字 1 、 0 和字母 O 、l 有时分不清楚，所以，没有数字 1 、 0
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z"};
        List<String> list = Arrays.asList(beforeShuffle);//将数组转换为集合
        Collections.shuffle(list);  //打乱集合顺序
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s); //将集合转化为字符串
        }
        return sb.substring(3, 8);
    }
}
