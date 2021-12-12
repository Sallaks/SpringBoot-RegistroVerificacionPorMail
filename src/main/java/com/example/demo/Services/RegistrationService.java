package com.example.demo.Services;

import com.example.demo.Entitys.ConfirmationToken;
import com.example.demo.Entitys.User;
import com.example.demo.Exception.ErrorService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public void register(String username, String email, String password1, String password2) throws ErrorService {

        validate(username, email, password1, password2);

        User user = new User(username, email, password1);

        String token = userService.signUpUser(user);

        String link = "http://localhost:8080/confirm_register?token=" + token;

        emailService.send(email, link);

    }

    public void validate(String username, String email, String password1, String password2) throws ErrorService {

        if (username == null || username.isEmpty()) {
            throw new ErrorService("Coloque un nombre de usuario");
        }

        if (email == null || email.isEmpty()) {
            throw new ErrorService("Coloque un mail");
        }

        if (!password1.equals(password2)) {
            throw new ErrorService("Las contraseñas deben ser iguales");
        }

    }

    @Transactional
    public void confirmToken(String token) throws ErrorService {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getComfirmDate() != null) {
            throw new ErrorService("el email ya fue confirmado");
        }

        LocalDateTime expiresDate = confirmationToken.getExpiresDate();

        if (expiresDate.isBefore(LocalDateTime.now())) {
            throw new ErrorService("token expirado");
        }

        confirmationTokenService.setConfirmedDate(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());

    }

}
