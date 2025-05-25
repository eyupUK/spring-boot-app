package com.eyup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("/offer")
public class OfferController {
    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());
    static {
        try {
            // Configure the logger with handler and formatter
            FileHandler fileHandler = new FileHandler("offerSearch.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize logger handler.", e);
        }
    }

    @GetMapping("/")
    public String showOfferForm() {
        LOGGER.info("Showing offer page...");

        return "offerForm.html";
    }

    @PostMapping("/search")
    public static String searchOffer() {
        LOGGER.info("Searching for offers...");

        return "getoffer.html";
    }
}
