package gr.aueb.cf.taskapp.controllers;

import gr.aueb.cf.taskapp.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;

//    @PostMapping("/send-mail")
//    public void sendMail(@Valid @RequestBody String mail, @RequestBody String subject, @RequestBody String body) {
//        mailService.sendMail(mail, subject, body);
//    }
    @GetMapping("/send-mail")
    public String sendMail() {
        mailService.sendMail("springbootermail@gmail.com", "Spring Booter Mail", "Spring Booter Mail");
        return "Email sent";
    }
}
