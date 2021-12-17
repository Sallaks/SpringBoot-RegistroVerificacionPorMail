package com.example.demo.Configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Override
     public void onAuthenticationFailure(HttpServletRequest request, 
             HttpServletResponse response, 
             AuthenticationException exception) 
             throws IOException, ServletException {
        
        super.setDefaultFailureUrl("/login?error");
         
        super.onAuthenticationFailure(request, response, exception);
        
        String errorMessage = "Nombre de usuario o Contraseña incorrectos";
        
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Usuario no activado, porfavor revise su mail para activar su cuenta";
            
        }
        
        //TODO: Hace una mejor implementacion del manejo de esta exception
        else if (exception.getMessage().equalsIgnoreCase("org.springframework.security.core.userdetails.UsernameNotFoundException: Usuario no encontrado")) {
            errorMessage = "Usuario no encontrado";
            
        }
        
        request.getSession().setAttribute("error", errorMessage); 
    }  
}