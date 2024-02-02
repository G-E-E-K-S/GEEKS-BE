package com.example.geeks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yml")
public class MailService {
    private final JavaMailSender emailSender;

    public static final String ePw = createKey();

    public static final StringBuilder password = new StringBuilder();

    private static final char[] CHAR_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '$', '%', '^', '&' };

    private MimeMessage createMessage(String to) throws Exception{
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);
        // https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/banner_pattern.svg
        // https://drive.google.com/uc?export=view&amp;id=1Sq-EX7P31_gwDoSmUh5SJDD-8z1dfhKq

        String msgg =
                "<!DOCTYPE html>\n" +
                        "<html style=\"padding: 0;margin: 0; font-family: Pretendard;word-break: break-all;\">\n" +
                        "  <head style=\"background-color: #fff4cd;height: 19vh;display: flex;justify-content: center;align-items: center;\">\n" +
                        "    <meta charset=\"UTF-8\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                        "    <title>Email</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"./Email.css\" />\n" +
                        "  </head>\n" +
                        "  <header>\n" +
                        "    <img class=\"logo\" src=\"https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/geeksLogo.svg\" />\n" +
                        "  </header>\n" +
                        "  <body>\n" +
                        "    <div style=\"padding-left: 6.94vw;\">\n" +
                        "      <div style=\"color: #525252;font-size: 24px;font-weight: 500;margin-top: 9.19vh;\">\n" +
                        "        안녕하세요, 긱스에서 요청하신 인증번호를 보내드려요.\n" +
                        "      </div>\n" +
                        "      <div style=\"color: #333;font-size: 64px;font-weight: 500;line-height: normal;margin-top: 11.63vh;margin-bottom: 11.7vh;\">"+ ePw +"</div>\n" +
                        "      <div style=\"color: #525252;font-size: 32px;font-style: normal;font-weight: 500;\">위 인증번호 4자리를 인증번호 입력창에 정확히 입력해 주세요.</div>\n" +
                        "      <div style=\"color: #525252;font-size: 24px;font-style: normal;font-weight: 500;margin-top: 3.46vh;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "  <footer>\n" +
                        "    <div style=\"color: #525252;font-size: 24px;font-style: normal;font-weight: 500;margin-top: 3.46vh;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "  </footer style=\"position: fixed;bottom: 0;width: 100%;background-color: #fff4cd;height: 15vh;padding-top: 5.37vh;padding-left: 6.94vw;\">\n" +
                        "</html>\n";

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("[GEEKS] 회원가입 이메일 인증번호");//제목

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ofi1234@naver.com","GEEKS"));

        return message;
    }


    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) { // 인증코드 8자리
            key.append((rnd.nextInt(10)));
        }

        return key.toString();
    }

    public String sendSimpleMessage(String to) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("to = " + to);
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    public String sendPasswordMessage(String to) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("to = " + to);
        MimeMessage message = createPasswordMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return password.toString();
    }

    private MimeMessage createPasswordMessage(String to) throws Exception{
        generatePassword();
        System.out.println("보내는 대상 : " + to);
        System.out.println("임시 비밀 번호 : " + password);
        // https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/banner_pattern.svg
        // https://drive.google.com/uc?export=view&amp;id=1Sq-EX7P31_gwDoSmUh5SJDD-8z1dfhKq

        String msgg =
                "<!DOCTYPE html>\n" +
                        "<html style=\"padding: 0;margin: 0; font-family: Pretendard;word-break: break-all;\">\n" +
                        "  <head style=\"background-color: #fff4cd;height: 19vh;display: flex;justify-content: center;align-items: center;\">\n" +
                        "    <meta charset=\"UTF-8\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                        "    <title>Email</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"./Email.css\" />\n" +
                        "  </head>\n" +
                        "  <header>\n" +
                        "    <img class=\"logo\" src=\"https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/geeksLogo.svg\" />\n" +
                        "  </header>\n" +
                        "  <body>\n" +
                        "    <div style=\"padding-left: 6.94vw;\">\n" +
                        "      <div style=\"color: #525252;font-size: 24px;font-weight: 500;margin-top: 9.19vh;\">\n" +
                        "        안녕하세요, 긱스에서 요청하신 임시비밀번호를 보내드려요.\n" +
                        "      </div>\n" +
                        "      <div style=\"color: #333;font-size: 64px;font-weight: 500;line-height: normal;margin-top: 11.63vh;margin-bottom: 11.7vh;\">"+ password +"</div>\n" +
                        "      <div style=\"color: #525252;font-size: 32px;font-style: normal;font-weight: 500;\">위 인증번호 4자리를 인증번호 입력창에 정확히 입력해 주세요.</div>\n" +
                        "      <div style=\"color: #525252;font-size: 24px;font-style: normal;font-weight: 500;margin-top: 3.46vh;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "  <footer>\n" +
                        "    <div style=\"color: #525252;font-size: 24px;font-style: normal;font-weight: 500;margin-top: 3.46vh;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "  </footer style=\"position: fixed;bottom: 0;width: 100%;background-color: #fff4cd;height: 15vh;padding-top: 5.37vh;padding-left: 6.94vw;\">\n" +
                        "</html>\n";

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("[GEEKS] 임시 비밀번호");//제목

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ofi1234@naver.com","GEEKS"));

        return message;
    }


    public static String generatePassword() {
        SecureRandom random = new SecureRandom();

        int passwordLength = random.nextInt(8) + 8;

        boolean hasSpecialCharacter = false;

        while (password.length() < passwordLength) {
            int randomIndex = random.nextInt(CHAR_SET.length);
            char randomChar = CHAR_SET[randomIndex];
            password.append(randomChar);

            if (isSpecialCharacter(randomChar)) {
                hasSpecialCharacter = true;
            }
        }

        if (!hasSpecialCharacter) {
            int randomIndex = random.nextInt(password.length());
            char randomSpecialChar = CHAR_SET[random.nextInt(CHAR_SET.length - 10) + 10]; // 특수문자만 선택
            password.setCharAt(randomIndex, randomSpecialChar);
        }

        return password.toString();
    }

    private static boolean isSpecialCharacter(char c) {
        return c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&';
    }

}
