/*
 * MailService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;


@Component
public class MailService implements IMailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    private JavaMailSenderImpl mailSender;

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.util.mail.IMailService#sendSimpleMail(java.lang.String,
     * java.lang.String[], java.lang.String, java.lang.String, java.util.Map)
     */
    public void sendSimpleMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap) {

        LOGGER.info("Mail Sending Process Begins...");
        String username = mailConfigMap
                .get(ESESystem.USER_NAME != null ? ESESystem.USER_NAME
                        : "");
        String password = mailConfigMap
                .get(ESESystem.PASSWORD != null ? ESESystem.PASSWORD
                        : "");
        String mailHost = mailConfigMap
                .get(ESESystem.MAIL_HOST != null ? ESESystem.MAIL_HOST
                        : "");
        String port = mailConfigMap
                .get(ESESystem.PORT != null ? ESESystem.PORT
                        : "");

        try {
            SimpleMailMessage simpleMailMsg = new SimpleMailMessage();
            simpleMailMsg.setFrom(from);
            simpleMailMsg.setSubject(subject);
            simpleMailMsg.setTo(to);
            simpleMailMsg.setText(message);
            mailSender = new JavaMailSenderImpl();
            SMTPConfiguration(mailHost, port, username, password);
            mailSender.send(simpleMailMsg);
            LOGGER.info("Mail Sending Process Ends");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.service.IMailService#sendMail(java.lang.String, java.lang.String[],
     * java.lang.String, java.lang.String, java.util.Map, boolean)
     */
    public void sendMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap, boolean isHTMLContent) {

        LOGGER.info("Mail Sending Process Begins...");
        String username = mailConfigMap
                .get(ESESystem.USER_NAME != null ? ESESystem.USER_NAME
                        : "");
        String password = mailConfigMap
                .get(ESESystem.PASSWORD != null ? ESESystem.PASSWORD
                        : "");
        String mailHost = mailConfigMap
                .get(ESESystem.MAIL_HOST != null ? ESESystem.MAIL_HOST
                        : "");
        String port = mailConfigMap
                .get(ESESystem.PORT != null ? ESESystem.PORT
                        : "");

        try {
            mailSender = new JavaMailSenderImpl();

            MimeMessage msg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, isHTMLContent);

            SMTPConfiguration(mailHost, port, username, password);
            mailSender.send(msg);
            LOGGER.info("Mail Sending Process Ends");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * @see com.ese.service.util.IMailService#sendAttachmentMail(java.lang.String,
     * java.lang.String[], java.lang.String, java.lang.String, java.util.Map, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public void sendAttachmentMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap, Map<String, byte[]> mailAttachment) {

        LOGGER.info("Mail Sending Process Begins...");
        String username = mailConfigMap
                .get(ESESystem.USER_NAME != null ? ESESystem.USER_NAME
                        : "");
        String password = mailConfigMap
                .get(ESESystem.PASSWORD != null ? ESESystem.PASSWORD
                        : "");
        String mailHost = mailConfigMap
                .get(ESESystem.MAIL_HOST != null ? ESESystem.MAIL_HOST
                        : "");
        String port = mailConfigMap
                .get(ESESystem.PORT != null ? ESESystem.PORT
                        : "");

        try {
            mailSender = new JavaMailSenderImpl();
            SMTPConfiguration(mailHost, port, username, password);

            MimeMessage msg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            SimpleMailMessage simpleMailMsg = new SimpleMailMessage();
            simpleMailMsg.setFrom(from);
            simpleMailMsg.setSubject(subject);
            simpleMailMsg.setTo(to);
            simpleMailMsg.setText(message);

            helper.setFrom(simpleMailMsg.getFrom());
            helper.setTo(simpleMailMsg.getTo());
            helper.setSubject(simpleMailMsg.getSubject());
            helper.setText(simpleMailMsg.getText());

            Iterator itr = mailAttachment.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                helper.addAttachment((String) entry.getKey(),
                        new ByteArrayResource((byte[]) entry.getValue()));
            }

            mailSender.send(msg);
            LOGGER.info("Mail Sending Process Ends");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * SMTP configuration.
     * @param hostName the host name
     * @param port the port
     * @param userName the user name
     * @param password the password
     */
    private void SMTPConfiguration(String hostName, String port, String userName, String password) {

        mailSender.setProtocol(IMailService.SMTP);
        mailSender.setHost(hostName);
        mailSender.setPort(Integer.valueOf(port));
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        Properties props = new Properties();
        props.setProperty("mail.smtp.submitter", userName);
        props.put(IMailService.STARTTLS_ENABLE, "true");
        props.put(IMailService.SMTP_AUTHENTICATION, "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.ssl.trust", hostName);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
        mailSender.setJavaMailProperties(props);
        mailSender.setJavaMailProperties(props);
    }

    /**
     * Gets the mail sender.
     * @return the mail sender
     */
    public JavaMailSenderImpl getMailSender() {

        return mailSender;
    }

    /**
     * Sets the mail sender.
     * @param mailSender the new mail sender
     */
    public void setMailSender(JavaMailSenderImpl mailSender) {

        this.mailSender = mailSender;
    }

}
