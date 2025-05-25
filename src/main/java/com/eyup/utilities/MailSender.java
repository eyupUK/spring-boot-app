package com.eyup.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailSender {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    static WebDriver driver = new ChromeDriver();

    static String imagePath = System.getProperty("user.dir") + "/screenshots/block_scheduled.jpg";
    static  String to = ConfigurationReader.get("email");
    public static void sendEmail(String subject, String message) throws InterruptedException, AWTException {
        logger.info("Sending email with subject: " + subject + " and message: " + message);
        setUp(false);
        logger.info("Set up the driver");
        goToWebsite();
        logger.info("Navigated to proton email website");
        login("etzc@protonmail.com", "Aa,132639");
        logger.info("Logged in to proton email successfully");
        goToComposeEmail();
        logger.info("Navigated to compose email page");
        setEmail(to, subject, message);
        logger.info("Email composed successfully");
        tearDown();
        logger.info("Email sent successfully");
    }

    public static void sendEmail(String to, String subject, String message) throws InterruptedException, AWTException {
        setUp(false);
        goToWebsite();
        login("etzc@protonmail.com", "Aa,132639");
        goToComposeEmail();
        setEmail(to, subject, message);
        tearDown();
    }

    private static void setUp(boolean headless) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private static void goToWebsite() {
        driver.get("https://account.proton.me/mail");
    }

    private static void login(String username, String password) {
        driver.findElement(By.cssSelector("input#username")).sendKeys(username);
        driver.findElement(By.cssSelector("input#password")).sendKeys(password, Keys.ENTER);

    }


    private static void goToComposeEmail(){
        waitForPresence("New message", By.xpath("//button[text()='New message']"));
        driver.findElement(By.xpath("//button[text()='New message']")).click();
    }

    private static void setEmail(String to, String subject, String message) throws InterruptedException, AWTException {

        waitForPresence("to email address", By.xpath("//input[contains(@id,'to-composer')]"));
        driver.findElement(By.xpath("//input[contains(@id,'to-composer')]")).sendKeys(to);
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='Subject']")).sendKeys(subject);
        Thread.sleep(500);
        //driver.findElement(By.xpath("//input[@type='file']")).sendKeys(imagePath);
        Thread.sleep(1500);
        //driver.findElement(By.xpath("//button[.='Attachment']")).click();
        Thread.sleep(4000);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@title='Email composer']"));
        driver.switchTo().frame(iframe);
        Thread.sleep(500);
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        WebElement messageInput = driver.findElement(By.xpath("//*[@id=\"rooster-editor\"]/div[1]"));
        messageInput.sendKeys(message);

        // html file attachment can be implemented here

        Thread.sleep(500);
        new Actions(driver).keyDown(cmdCtrl).sendKeys(Keys.ENTER).keyUp(cmdCtrl).perform();
        driver.switchTo().defaultContent();
        waitForPresence("Message sent ", By.xpath("//span[.='Message sent.']"));
        Thread.sleep(2000);
    }

    private static void tearDown() {
        driver.quit();
    }

    private static List<WebElement> waitForPresence(String elementName, By by){
        List<WebElement> elementList = driver.findElements(by);
        while(elementList.isEmpty()){
            System.out.println(elementName + " not displayed yet!");
            elementList = driver.findElements(by);
        }
        return elementList;
    }

}
