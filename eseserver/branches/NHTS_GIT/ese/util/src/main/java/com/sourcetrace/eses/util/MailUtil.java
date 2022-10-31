package com.sourcetrace.eses.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class.getName());

    /**
     * send a simple mail format.
     * @param to mail ids separated by comma
     * @param subject the subject
     * @param title the title
     * @param message the message
     */
    public static void send(String to, String subject, String title, String message) {

        if (!StringUtil.isEmpty(to)) {
            JavaMailSenderImpl mailSender;
            SimpleMailMessage msg = new SimpleMailMessage();
            String[] maillist = {to};
            msg.setFrom("DATAGREEN <eseserver@sourcetrace.com>");
            String mailMessage = message;
            String sub = StringUtil.isEmpty(subject) ? "(No Subject)" : subject;
            msg.setSubject(sub);
            msg.setTo(maillist);
            msg.setText(getEmailText(title, mailMessage));
            try {
                mailSender = new JavaMailSenderImpl();
                configurationOfsmpt(mailSender);
                mailSender.send(msg);
                LOGGER.info("Mail has been Sent");
            } catch (MailException ex) {
                LOGGER.error("Error sending email", ex);
            }
        } else {
            LOGGER.info("No Send Address Found");
        }
    }
    
    public static void sendWithCC(String to, String subject, String title, String message, String cc) {

        if (!StringUtil.isEmpty(to)) {
            JavaMailSenderImpl mailSender;
            SimpleMailMessage msg = new SimpleMailMessage();
            String[] maillist = {to};
            msg.setFrom("ESE Server<eseserver@sourcetrace.com>");
            String mailMessage = message;
            String sub = StringUtil.isEmpty(subject) ? "(No Subject)" : subject;
            msg.setSubject(sub);
            msg.setTo(maillist);
            msg.setCc(cc);
            msg.setText(getEmailText(title, mailMessage));
            try {
                mailSender = new JavaMailSenderImpl();
                configurationOfsmpt(mailSender);
                mailSender.send(msg);
                LOGGER.info("Mail has been Sent");
            } catch (MailException ex) {
                LOGGER.error("Error sending email", ex);
            }
        } else {
            LOGGER.info("No Send Address Found");
        }
    }


    private static void configurationOfsmpt(JavaMailSenderImpl mailSender) {

		mailSender.setProtocol("smtp");
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("eseserver@sourcetrace.com");
		mailSender.setPassword("eseserver123");
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		mailSender.setJavaMailProperties(props);
    }

    private static String getEmailText(String title, String messsage) {

        StringBuffer model = new StringBuffer();
        model.append("Dear " + title + ",\n\n");
        model.append(messsage + "\n\n");
        return model.toString();
    }

}
