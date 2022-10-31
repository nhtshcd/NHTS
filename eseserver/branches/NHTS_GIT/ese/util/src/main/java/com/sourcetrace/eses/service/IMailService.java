/*
 * IMailService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.util.Map;

public interface IMailService {

    public static final String SMTPS = "smtps";
    public static final String SMTPS_AUTHENTICATION = "mail.smtps.auth";

    public static final String SMTP = "smtp";
    public static final String SMTP_AUTHENTICATION = "mail.smtp.auth";
    public static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    /**
     * Send simple mail.
     * @param from the from
     * @param to the to
     * @param subject the subject
     * @param message the message
     * @param mailConfigMap the mail config map
     */
    public void sendSimpleMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap);

    /**
     * Send attachment mail.
     * @param from the from
     * @param to the to
     * @param subject the subject
     * @param message the message
     * @param mailConfigMap the mail config map
     * @param mailAttachment the mail attachment
     */
    public void sendAttachmentMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap, Map<String, byte[]> mailAttachment);

    /**
     * Send mail.
     * @param from the from
     * @param to the to
     * @param subject the subject
     * @param message the message
     * @param mailConfigMap the mail config map
     * @param isHTMLContent the is html content
     */
    public void sendMail(String from, String[] to, String subject, String message,
            Map<String, String> mailConfigMap, boolean isHTMLContent);

}