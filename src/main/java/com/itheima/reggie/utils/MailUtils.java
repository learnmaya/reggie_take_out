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
        sendTestMail("405350634@qq.com", new MailUtils().achieveCode());
    }

    public static void sendTestMail(String email, String code) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.user", "405350634@qq.com");
        props.put("mail.password", "webouzghwvoacbbi");
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        Session mailSession = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(mailSession);
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        InternetAddress to = new InternetAddress(email);
        message.setRecipient(RecipientType.TO, to);
        message.setSubject("Welcome to Reggie's Takeout Services!");

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

    public static String achieveCode() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z"};
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.substring(3, 8);
    }
}
