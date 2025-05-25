package com.eyup.controllers;

import com.eyup.models.SignInModel;
import com.eyup.new_services.SignIn;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


import java.awt.*;
import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("/signin")
public class SignInController {
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());
    static {
        try {
            // Configure the logger with handler and formatter
            FileHandler fileHandler = new FileHandler("signIn.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize logger handler.", e);
        }
    }

    @GetMapping("/")
    public String signIn(Model model) {
        model.addAttribute("signIn", new SignInModel());
        return "signin.html";
    }

    @PostMapping("/processSignIn")
    public String processSignIn(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @Valid SignInModel signInModel, BindingResult bindingResult, Model model) throws IOException, InterruptedException, AWTException {
        LOGGER.info("email and password: " + email + " " + password);
        if (bindingResult.hasErrors()) {
            model.addAttribute("signIn", signInModel);
            return "signin.html";
        }
        String pageOTP = SignIn.signIn(email, password);

        model.addAttribute("dynamicContent", pageOTP);

        LOGGER.info("pageOTP: " + pageOTP);
        return "submitOTP.html";
    }
}
