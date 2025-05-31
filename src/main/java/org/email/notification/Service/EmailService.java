package org.email.notification.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.email.notification.DTO.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid;
    @Value("${sendgrid.from-email}")
    private String fromEmail;


    public EmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public String sendEmail(EmailRequest request) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(request.getTo());

        Content content = new Content("text/html", request.getBody());
        Mail mail = new Mail(from, request.getSubject(), to, content);

        // Optional CC
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        if (request.getCc() != null) {
            for (String cc : request.getCc()) {
                personalization.addCc(new Email(cc));
            }
        }
        mail.addPersonalization(personalization);

        Request sgRequest = new Request();
        sgRequest.setMethod(Method.POST);
        sgRequest.setEndpoint("mail/send");
        sgRequest.setBody(mail.build());

        Response response = sendGrid.api(sgRequest);

        return "Status Code: " + response.getStatusCode();
    }
}
