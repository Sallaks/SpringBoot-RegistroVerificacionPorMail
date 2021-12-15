package com.example.demo.Services;

import com.example.demo.Entitys.ConfirmationToken;
import com.example.demo.Exception.ErrorService;
import com.example.demo.Repositories.ConfirmationTokenRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    
   public ConfirmationToken getToken(String token) throws ErrorService {
       Optional<ConfirmationToken> response = confirmationTokenRepository.findByToken(token);
       
       if (response.isPresent()) {
           ConfirmationToken confirmationToken = 
                   confirmationTokenRepository.findByToken(token).get();
           return confirmationToken;
       }
       else{
           throw new ErrorService("Token no encontrado");
       }
    }
   
   public int setConfirmedDate(String token) {
        return confirmationTokenRepository.updateConfirmedDate(
                token, LocalDateTime.now());
    }
   
  
    
}
