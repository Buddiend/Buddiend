package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendVerificationEmail(User user, String subject)
            throws MessagingException, UnsupportedEncodingException;

    void sendForgotPasswordEmail(User user, String subject, String token)
            throws MessagingException, UnsupportedEncodingException;
}
