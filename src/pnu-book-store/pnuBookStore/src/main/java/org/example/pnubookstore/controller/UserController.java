package org.example.pnubookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.example.pnubookstore.domain.user.service.UserEmailVerificationService;
import org.example.pnubookstore.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserEmailVerificationService userEmailVerificationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage() {

        return "email-verification";
    }
    @PostMapping("/sendVerificationCode")
    public String emailVerify(@RequestParam String email, HttpServletRequest request){

        userService.emailVerify(email, request);

        return "index"; // 이메일 전송이 완료됐다는 표시, 리다이렉트
    }

    @GetMapping("/signUp/after-email")
    public String afterEmail(@RequestParam String uuid, Model model){
        // 유효한 uuid인지 확인
        EmailVerification email = userEmailVerificationService.isUserEmailValid(uuid);

        // not valid
        if(email == null){
            return "index";
        }

        model.addAttribute("email", email.getEmail());
        return "signUp";
    }


    @PostMapping("/signUp")
    public String afterEmailForm(@ModelAttribute CreateUserDto request){
        userService.createUser(request);
        return "redirect:/";
    }

}
