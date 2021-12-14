package com.example.demo.Services;

import com.example.demo.Entitys.ConfirmationToken;
import com.example.demo.Entitys.User;
import com.example.demo.Exception.ErrorService;
import com.example.demo.Repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public String signUpUser(User user) throws ErrorService {

        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {

            // si no esta confirmado, pero esta registrado
            // falta ver si el tiempo pasa para crear un token nuevo
            if (!user.isEnabled()) {
                String token = UUID.randomUUID().toString();

                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        user
                );
                confirmationTokenService.saveConfirmationToken(
                        confirmationToken);

                String link = "http://localhost:8080/cobfirm_register?token=" + token;

                emailService.send(user.getEmail(), link);

                throw new ErrorService("Debe activar el usuario, porfavor revise su mail");
            } else {
                throw new ErrorService("El email ya esta registrado");
            }

        }

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        // genero el token
        
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return token;
    }

    public int enableUser(String email) {
        System.out.println("Entre a enable user");
        return userRepository.enableUser(email);
    }

    
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<User> response = userRepository.findByEmail(email);

        if (response.isPresent()) {
            User user = userRepository.findByEmail(email).get();
            return user;
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
