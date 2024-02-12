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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yml")
public class MailService {
    private final JavaMailSender emailSender;

    public static String ePw = "";

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
                "<div style=\"width: 100vw;max-width: 100%;box-sizing: border-box; height:180px; right:0; left:0; top:0; background-color: #FFD540; margin: 0; padding: 0;display: flex;justify-content: center;align-items: center;\">\n" +
                        "    <img style=\"margin: 69px 0;\" src=\"https://bucket-geeks.s3.ap-northeast-2.amazonaws.com/logo.svg\"/>\n" +
                        "  </div>\n" +
                        "  <div style=\"width: 95vw;margin: 0 auto;padding: 0;background-color: #fff;box-sizing: border-box;\">\n" +
                        "    <div style=\"padding: 0px 1rem\">\n" +
                        "      <div style=\"color: #525252;font-size: 1.2rem;font-weight: 500;margin-top: 9.19vh;\">\n" +
                        "        안녕하세요, 긱스에서 요청하신 인증번호를 보내드려요.\n" +
                        "      </div>\n" +
                        "      <div style=\"color: #333;font-size: 3.3rem;font-weight: 500;line-height: normal;margin-top: 4rem;margin-bottom: 2rem;\">"+ePw+"</div>\n" +
                        "      <div style=\"color: #525252;font-size: 1.25rem;font-style: normal;font-weight: 500;\">위 인증번호 4자리를 인증번호 입력창에 정확히 입력해주세요.</div>\n" +
                        "      <div style=\"color: #525252;font-size: 1rem;font-style: normal;font-weight: 500;margin-top: 1rem;margin-bottom:3rem;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "  <div style=\"background-color: #FFD540;height: 15vh;padding: 10px 1rem 0px 1rem;width: 100vw;box-sizing: border-box;position: absolute;bottom: 0;max-width: 100%;\">\n" +
                        "    <div style=\"color: #525252;font-size: 1rem;font-style: normal;font-weight: 500;margin-top: 2.46vh;\">인증번호를 요청하지 않았을 경우 본 이메일을 무시해 주세요.</div>\n" +
                        "  </div>\n";

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("[GEEKS] 회원가입 이메일 인증번호");//제목

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ofi1234@naver.com","GEEKS"));

        return message;
    }


    public static String createKey(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        Set<Integer> usedNumbers = new HashSet<>();

        while (code.length() < length) {
            SimpleDateFormat format1 = new SimpleDateFormat ( "ss");
            Date time = new Date();
            int time1 = Integer.parseInt(format1.format(time));

            int randomNumber = random.nextInt(10) * time1;// 0부터 9 사이의 랜덤 숫자 생성

            if (randomNumber >= 10) {
                randomNumber = randomNumber % (randomNumber / 10);
            }

            // 생성된 숫자가 중복되지 않도록 확인
            if (!usedNumbers.contains(randomNumber)) {
                code.append(randomNumber);
                usedNumbers.add(randomNumber);
            }
        }

        return code.toString();
    }


    public String sendSimpleMessage(String to) throws Exception {
        ePw = createKey(4);

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
