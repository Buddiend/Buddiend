package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String senderName;
    private String verificationContent = "Dear [[name]],<br>"
            + "Here is your verification code to verify your account:<br>"
            + "<h3>\"[[VERIFICATION_CODE]]\"</h3>"
            + "Thank you,<br>"
            + "Buddiend.";

    private String forgotPasswordContent = "Dear [[name]],<br>"
            + "Here is link to reset your account password:<br>"
            + "<a href = \"[[TOKEN]]\"> Link </a>"
            + "Thank you,<br>"
            + "Buddiend.";


    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.senderName = "Buddiend";;
        this.fromAddress = "bojansimiciev@gmail.com";
    }

    @Override
    public void sendVerificationEmail(User user, String subject)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String content = this.verificationContent;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());

        content = content.replace("[[VERIFICATION_CODE]]", user.getVerification_code());

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendForgotPasswordEmail(User user, String subject, String token)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String content = this.forgotPasswordContent;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());

//        String link = "https://buddiend.com/password/reset/" + token;  --For Browser
        String link = "http://localhost:9090/password/reset/" + token;
        content = content.replace("[[TOKEN]]", link);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
