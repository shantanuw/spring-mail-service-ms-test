package com.emailtest.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author shantanu.wagh
 * @since 12/12/19
 */
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostConstruct
    public void sendEmail() {
        String to = "email@gmail.com";
        String subject = "Test EMail";
        String content = "<html><body><p>TestEmail</p></body>";
        boolean isMultipart = false;
        boolean isHtml = true;

        if (log.isDebugEnabled()) {
            JavaMailSenderImpl impl = (JavaMailSenderImpl) javaMailSender;
            log.debug(impl.getHost());
            log.debug(Integer.valueOf(impl.getPort()).toString());
            log.debug(impl.getUsername());
            log.debug(impl.getPassword());
            log.debug(impl.getJavaMailProperties().toString());
        }

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom("clientsupport@farrellday.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }
        catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }
}
