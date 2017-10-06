package ru.loadmeter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class LoadMeter {

    WebDriver wd;

    @Test
    public void loadTime() {

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.OFF);
        logs.enable(LogType.CLIENT, Level.SEVERE);
        logs.enable(LogType.DRIVER, Level.WARNING);
        logs.enable(LogType.PERFORMANCE, Level.INFO);
        logs.enable(LogType.SERVER, Level.ALL);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();// .firefox();
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

        //WebDriver driver = new FirefoxDriver(desiredCapabilities);

        //ChromePerformanceLoggingPreferences perfLogPrefs = new ChromePerformanceLoggingPreferences();
        //ChromeOptions options = new ChromeOptions();
        //perfLogPrefs.AddTracingCategories(new string[] { "devtools.timeline" });
        //options.PerformanceLoggingPreferences = perfLogPrefs;
        //options.AddAdditionalCapability(CapabilityType.EnableProfiling, true, true);

        String browser = BrowserType.CHROME;
        if (Objects.equals(browser, BrowserType.FIREFOX)) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (Objects.equals(browser, BrowserType.CHROME)) {



            wd = new ChromeDriver(desiredCapabilities);
        } else if (Objects.equals(browser, BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }


        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        long startTime = System.currentTimeMillis();
        wd.get("http://t2ru-crmfe-tst.corp.tele2.ru/Tele2/main.aspx");

        long timeSpent = System.currentTimeMillis() - startTime;
        Logs loggs = wd.manage().logs();

        LogEntries logEntries = loggs.get(LogType.PERFORMANCE);

        for (LogEntry logEntry : logEntries) {
            System.out.println("Time: " + String.format("%tF %<tT.%<tL", logEntry.getTimestamp() ) + "(" + logEntry.getTimestamp() + ") Log: " + logEntry.getMessage());
        }

       // for (LogEntry entry : wd.manage().logs().get(LogType.PERFORMANCE)) {
       //     System.out.println(entry.toString());
       // }
        System.out.println("Time load " + timeSpent);
        wd.close();

    }
}
