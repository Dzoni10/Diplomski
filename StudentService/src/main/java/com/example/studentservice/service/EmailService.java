package com.example.studentservice.service;


import com.example.studentservice.dto.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendVerificationEmail(UserDTO userDTO, String link) throws MailException, InterruptedException, MessagingException {

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        helper.setTo(userDTO.email);
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setSubject("SecurityCertificateApp™ - Verification mail");

        String verificationUrl = link;
        String htmlMsg = "<p>Zdravo " + userDTO.name + ",</p>"
                + "<p>Kliknite na link ispod da bi verifikovao svoj nalog</p>"
                + "<a href='" + verificationUrl + "'>Verifikuj nalog</a>"
                + "<p>Srdacan pozdrav,</p>"
                +"<p>IT sluzba fakulteta</p>";

        helper.setText(htmlMsg, true);
        mailSender.send(mail);
    }

    @Async
    public void sendPasswordRecoveryEmail(UserDTO userDTO, String link) throws MailException, InterruptedException, MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        helper.setTo(userDTO.email);
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setSubject("SecurityCertificateApp™ - Password recovery ");

        String htmlMsg = "<p>Zdravo " + userDTO.name + ",</p>"
                + "<p>Da bi verifikovali lozinku kliknite na link ispod</p>"
                + "<a href='" +link + "'>Verifikuj lozinku</a>"
                +"<p>Srdacan pozdrav,</p>"
                +"<p>IT Sluzba fakulteta</p>";

        helper.setText(htmlMsg, true);
        mailSender.send(mail);
    }
}
