
package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    JavaMailSender javaMailSender;
    
    public String send(String email, String link) {

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Completar Registro Turistearg");
        mensaje.setFrom("turisteargspring@gmail.com");
        mensaje.setText("Click en el link para confirmar registro " + link);
        javaMailSender.send(mensaje);
        
        return "enviado";

    }
    
    
}
