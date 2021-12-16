package com.example.demo.Controllers;

import com.example.demo.Exception.ErrorService;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.ConfirmationTokenService;
import com.example.demo.Services.RegistrationService;
import com.example.demo.Services.UserService;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    
    @Autowired
    RegistrationService registrationService;
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("")
    public String index(){
        // mensaje de logueo existoso
        return "index.html";
    }
    
    @GetMapping("/register")
    public String displayRegister() {

        return "register";
    }
    
    @PostMapping("/register")
    public String Register(ModelMap model,RedirectAttributes redirAttrs, 
            @RequestParam String username, @RequestParam String email, 
            @RequestParam String password1, @RequestParam String password2) throws ErrorService, MalformedURLException {

        try {
            registrationService.register(username, email, password1, password2);
            
            redirAttrs.addFlashAttribute("success", "Revise su correo electronico para verficar el registro");
            
        } catch (ErrorService e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
            
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String login(ModelMap model, @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout, HttpServletRequest request) {

        // Agregue a /login html param.error utilizo el erro que envio a la url
//        if (error != null) {
//         
//        }

        if (logout != null) {
            model.put("logout", "Ha salido correctamente");
        }

        return "login";
    }
    
    @GetMapping("/confirm_register")
    public String confirm(@RequestParam("token") String token, RedirectAttributes redirAttrs) throws ErrorService{
        
        try {
            registrationService.confirmToken(token);
            redirAttrs.addFlashAttribute("success", "Registro completado!");
        } catch (ErrorService e) {
             redirAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "redirect:/login"; 
    }

}
