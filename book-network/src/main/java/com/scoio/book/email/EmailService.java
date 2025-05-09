package com.scoio.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String receiver, String sender,
                          EmailTemplateName emailTemplate,
                          String url, String activationCode,
                          String subject) throws MessagingException {
        String templateName;
        if(emailTemplate == null){
            templateName = "confirm-email";
        }
        else {
            templateName = emailTemplate.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED
        , StandardCharsets.UTF_8.name());

        Map<String, Object> properties = new HashMap<>();
        properties.put("sender", sender);
        properties.put("url", url);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("contact@solomon.com");
        helper.setSubject(subject);
        helper.setTo(receiver);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}
