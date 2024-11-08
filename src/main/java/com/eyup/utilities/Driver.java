/**
 * Driver class to manage WebDriver instances for different browsers.
 *
 * @author [eyupUK]
 * @version 1.0
 */
package com.eyup.utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Singleton class to get and manage WebDriver instances for different browsers.
 *
 * @author [eyupUK]
 * @version 1.0
 */
public class Driver {

    private Driver() {
    }

    /**
     * Singleton instance of the WebDriver.
     */
    private static WebDriver driver;

    /**
     * Get the WebDriver instance for the specified browser.
     *
     * @return the WebDriver instance for the specified browser.
     */
    public static WebDriver get() {

        if (driver == null) {
            String browser = "chrome-headless";
            synchronized (Driver.class) {
                switch (browser) {
                    case "chrome-local" -> {
                        System.setProperty("webdriver.chrome.driver", "browserdriver/chromedriver");
                        driver = new ChromeDriver();
                    }
                    case "chrome" -> {
                        ChromeOptions options = new ChromeOptions();
                        options.setAcceptInsecureCerts(true);
                        driver = new ChromeDriver(options);
                    }
                    case "chrome-headless" -> {
                        ChromeOptions options = new ChromeOptions();
                        options.setAcceptInsecureCerts(true);
                        options.addArguments("headless");
                        driver = new ChromeDriver(options);
                    }

                }
            }
        }
        return driver;
    }

    /**
     * Close the WebDriver instance.
     */
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}