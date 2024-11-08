package com.eyup.utilities;

import java.awt.*;
import java.time.LocalDateTime;

public class MailSenderTest {
    public static void main(String[] args) throws InterruptedException, AWTException {
        MailSender.sendEmail("test " + LocalDateTime.now().toString().substring(11,19), "This is a test\n");
    }
}
