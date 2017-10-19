package ru.loadmeter;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

public class LoadMeter {

    WebDriver wd;

    @Test(enabled = true)
    public void loadTime() throws IOException {

        LoggingPreferences logs = new LoggingPreferences();
        //logs.enable(LogType.BROWSER, Level.OFF);
        //logs.enable(LogType.CLIENT, Level.SEVERE);
        //logs.enable(LogType.DRIVER, Level.WARNING);
            logs.enable(LogType.DRIVER, Level.ALL);
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
            wd = new InternetExplorerDriver( desiredCapabilities);
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        long startTime = System.currentTimeMillis();
        wd.get("http://t2ru-crmfe-tst.corp.tele2.ru/Tele2/main.aspx");

        long timeSpent = System.currentTimeMillis() - startTime;

       // wd.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        wd.switchTo().frame("contentIFrame0");
        wd.switchTo().frame("IFRAME_LegalCustomerSearchArea");
       // wd.findElement(By.id("NameOnLegalCustomerSearchViewportTextField-inputEl")).sendKeys("Бора-Бора");
       // wd.findElement(By.id("SearchOnLegalCustomerSearchViewportButton")).click();
        String str = wd.findElement(By.id("button-1046")).getText();
        System.out.println(str);

        long timeSpentEl = System.currentTimeMillis() - startTime;

        File file = new File(String.format("log/log%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd_HHmm"))));
        Writer writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.write("Log1 start\n");
        /*
        Logs loggs = wd.manage().logs();
    //    LogEntries logEntries = loggs.get(LogType.PERFORMANCE);
        LogEntries logEntries = loggs.get(LogType.DRIVER);
        for (LogEntry logEntry : logEntries) {
            writer.write("Time: " + String.format("%tF %<tT.%<tL", logEntry.getTimestamp() ) + "(" + logEntry.getTimestamp() + ") Log: " + logEntry.getMessage() + "\n");
        }

        writer.write("Log1 over\n");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wd.get("http://t2ru-crmfe-tst.corp.tele2.ru/Tele2/main.aspx");

*/
        Logs loggs = wd.manage().logs();
      //  logEntries = loggs.get(LogType.PERFORMANCE);
        LogEntries logEntries = loggs.get(LogType.DRIVER);
      //  System.out.println(logEntries.getAll().stream().collect(Collectors.toList()).toString());
        writer.write(logEntries.getAll().stream().collect(Collectors.toList()).toString());
       /* for (LogEntry logEntry : logEntries) {
            writer.write("Time: " + String.format("%tF %<tT.%<tL", logEntry.getTimestamp() ) + "(" + logEntry.getTimestamp() + ") Log: " + logEntry.getMessage() + "\n");
        }*/
        writer.write("Time load " + timeSpent + "\n");
        writer.write("Time load " + str + ": " + timeSpentEl + "\n");
        writer.close();

        logEntries = loggs.get(LogType.PERFORMANCE);
        File filePerf = new File(String.format("log/logPERF%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd_HHmm"))));
        Writer writerPerf = null;
        try {
            writerPerf = new FileWriter(filePerf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writerPerf.write("Log1 start\n");
        writerPerf.write(logEntries.getAll().toString().replaceAll("\\[","\n["));// .stream().collect(Collectors.toList()).toString());
        writerPerf.write("Time load " + timeSpent + "\n");
        writerPerf.write("Time load " + str + ": " + timeSpentEl + "\n");
        writerPerf.close();

        wd.close();

    }

    @Test(enabled = true)
    public void loadPrintDoc() throws IOException {

        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
/*
        ieCapabilities.setCapability("nativeEvents", true);
        ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability("enablePersistentHover", true);
        ieCapabilities.setCapability("ignoreZoomSetting", true);*/


        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        ieCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,  "accept");
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability("nativeEvents", false);
        ieCapabilities.setCapability("enablePersistentHover", true);
        ieCapabilities.setCapability("requireWindowFocus", true);
        ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        wd = new InternetExplorerDriver(ieCapabilities);
        wd.get("http://t2ru-crmfe-tst.corp.tele2.ru:8080/Sales/Erf/ShowPrintErfDocumentAbonentsForm?erfId={08A72833-70C8-494A-A373-D39C9CF05FC0}&readOnly=false");
        //wd.findElement(By.id("button-1023")).sendKeys(org.openqa.selenium.Keys.CONTROL);
        wd.findElement(By.id("button-1023")).sendKeys(Keys.RETURN);

        try{
            Thread.sleep(2000); // Задержка для того, чтобы окно с предложением сохранить файл открылось
            Robot robot=new Robot();

            robot.setAutoDelay(80);
            robot.setAutoWaitForIdle(true);
            int keyCode = 67; // Код буквы "с" (русская раскладка) для того, чтобы закрыть окно с сохранением вложения (Alt + с)
                              // В англоязычнов браузере необходимо изменить на другой код буквы. Должно подойти KeyEvent.VK_S

            // Блок шагов перед переключением раскладки, для того, чтобы раскладка переключилась в правильном окне.
            // Актуально только для русской версии браузера с английским языком по умолчанию в системе
            // Не актуально для  браузера на виртуалке
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(keyCode);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(keyCode);

            // Блок переключения раскладки (Alt + Shift).
            // Не актуально для  браузера на виртуалке
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_SHIFT);

            // Блок закрытия окна с сохранением вложения (Alt + с)
            // Для корректной работы в настройках отключить уведомление об успешном окончании скачивания файлов
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(keyCode);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(keyCode);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(wd.findElement(By.id("button-1023")).getText());
        wd.close();
    }

    @Test(enabled = false)
    public void windowsSendKey() throws IOException {
/*

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.OFF);
        logs.enable(LogType.CLIENT, Level.SEVERE);
        logs.enable(LogType.DRIVER, Level.WARNING);
        logs.enable(LogType.PERFORMANCE, Level.INFO);
        logs.enable(LogType.SERVER, Level.ALL);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();// .chrome();// .firefox();
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

*/
        wd = new InternetExplorerDriver();

/*        String browser = BrowserType.IE;
        if (Objects.equals(browser, BrowserType.FIREFOX)) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (Objects.equals(browser, BrowserType.CHROME)) {
            wd = new ChromeDriver(desiredCapabilities);
        } else if (Objects.equals(browser, BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        long startTime = System.currentTimeMillis();
        */
        wd.get("http://t2ru-crmfe-tst.corp.tele2.ru:8080/Sales/Erf/ShowPrintErfDocumentAbonentsForm?erfId={08A72833-70C8-494A-A373-D39C9CF05FC0}&readOnly=false");

/*        long timeSpent = System.currentTimeMillis() - startTime;

        //wd.switchTo().frame("contentIFrame0");
        //wd.switchTo().frame("IFRAME_LegalCustomerSearchArea");

        // wd.findElement(By.id("NameOnLegalCustomerSearchViewportTextField-inputEl")).sendKeys("Бора-Бора");
        // wd.findElement(By.id("SearchOnLegalCustomerSearchViewportButton")).click();
        System.out.println("First: " + wd.manage().window().getClass().getTypeName());
        //wd.findElement(By.id("button-1023")).click();
        */

        wd.findElement(By.id("button-1023")).sendKeys(Keys.RETURN);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*

        HWND ie_hwnd = MyUser32.INSTANCE.FindWindowEx(null,null,"IEFrame", null);
        System.out.println("Before: 1 wnd "  + (ie_hwnd == null));
        HWND frm_hwnd = MyUser32.INSTANCE.FindWindowEx(ie_hwnd, null, "Frame Notification Bar", null);
        //HWND hWnd = User32.INSTANCE.FindWindow( "IEFrame", null);
        //hWnd = User32.INSTANCE.FindWindow(null, "Сохранить");

        System.out.println("Before: 2 wnd " + (frm_hwnd == null));
        HWND hWnd = MyUser32.INSTANCE.FindWindowEx(frm_hwnd, null, null, "Открыть");

        System.out.println("Before: 3 wnd" + (hWnd == null));

        int WM_LBUTTONDOWN = 0x0201;
        int WM_LBUTTONUP = 0x0202;
        int WM_SYSKEYDOWN = 0x0104;
        int WM_SYSKEYUP = 0x0105;

        System.out.println("Before: mess ");
        String strText =getWindowText(hWnd);
        System.out.println("strText: " + strText);

        //User32.INSTANCE.ShowWindow(hWnd, 9);

        User32.INSTANCE.PostMessage(hWnd, WM_LBUTTONDOWN, new WinDef.WPARAM(0), new WinDef.LPARAM(0));
        User32.INSTANCE.PostMessage(hWnd, WM_LBUTTONUP, new WinDef.WPARAM(0), new WinDef.LPARAM(0));
        System.out.println("After: ");
        */
/*MyUser32.INSTANCE.SetFocus(hWnd);
        MyUser32.INSTANCE.SendMessage(hWnd, WM_LBUTTONDOWN, new WinDef.WPARAM(1), new WinDef.LPARAM(1));
        MyUser32.INSTANCE.SendMessage(hWnd, WM_LBUTTONUP, new WinDef.WPARAM(1), new WinDef.LPARAM(1));*//*

        MyUser32.INSTANCE.SendMessage(ie_hwnd, WM_SYSKEYDOWN, new WinDef.WPARAM(225), new WinDef.LPARAM(1));
        MyUser32.INSTANCE.SendMessage(ie_hwnd, WM_SYSKEYUP, new WinDef.WPARAM(225), new WinDef.LPARAM(1));

        for (char c='а'; c<='я'; c++) {
            //System.out.println("code="+(int)c+"\tsumbol="+c);

        } String str = new String("с".getBytes("Cp1251"),"Cp866");//здесь можно любые кодирвки
//поставить все равно отрицательные значения будут
*/

        try{
            Robot robot=new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            Thread.sleep(100);
            robot.keyPress(67);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(67);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_ALT);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_SHIFT);

            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_ALT);
            Thread.sleep(100);
            robot.keyPress(67);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(67);
          /*  robot.keyPress(67);
            Thread.sleep(1000);
            robot.keyRelease(67);*/
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println(wd.findElement(By.id("button-1023")).getText());
/*

        //System.out.println( "PostMessage " + User32.instance.PostMessage(ie_hwnd,WinUser.WM_SYSKEYDOWN, new WinDef.WPARAM(WinUser.WM_SYSKEYDOWN),));
        //PostMessageA ie_hwnd, &H104&, &H12, &H20000001  'WM_SYSKEYDOWN, VK_MENU
        //PostMessageA ie_hwnd, &H104&, &H53, &H20000001  'WM_SYSKEYDOWN, S
        //PostMessageA ie_hwnd, &H101&, &H53, &HC0000001  'WM_KEYUP, S
        //PostMessageA ie_hwnd, &H101&, &H12, &HC0000001  'WM_KEYUP, VK_MENU

        //        User32.instance.ShowWindow(hwnd, 9);
       // User32.instance.SetForegroundWindow(hwnd);
        final User32 user32 = User32.INSTANCE;

        user32.EnumWindows(new WNDENUMPROC() {
            int count = 0;
            public boolean callback(HWND hWnd, Pointer arg1) {
                char[] windowText = new char[512];
                user32.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                RECT rectangle = new RECT();
                user32.GetWindowRect(hWnd, rectangle);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                // second condition is for visible and non minimised windows
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd)
                        && rectangle.left > -32000)) {
                    return true;
                }
                System.out.println("Found window with text " + hWnd
                        + ", total " + ++count + " Text: " + wText);
                return true;
            }
        }, null);

        System.out.println(wd.manage().window().getClass().getTypeName());
*/
/*
        long timeSpentEl = System.currentTimeMillis() - startTime;

        File file = new File(String.format("log/logPrint%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd_HHmm"))));
        Writer writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.write("Log1 start\n");
        Logs loggs = wd.manage().logs();
        LogEntries logEntries = loggs.get(LogType.PERFORMANCE);
        for (LogEntry logEntry : logEntries) {
            writer.write("Time: " + String.format("%tF %<tT.%<tL", logEntry.getTimestamp() ) + "(" + logEntry.getTimestamp() + ") Log: " + logEntry.getMessage() + "\n");
        }
        writer.write("Log1 over\n");
        writer.write("Time load " + timeSpent + "\n");
        writer.write("Time load " +  ": " + timeSpentEl + "\n");
        writer.close();
*/

        wd.close();

    }

    @Test(enabled = false)
    public void TestChar() {
       // for (int key = 0; key < 255; key++) {
            try {
                //Thread.sleep(5000);
                Robot robot = new Robot();
           /* robot.keyPress(KeyEvent.VK_ALT);
            Thread.sleep(1000);
            robot.keyPress(255);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(255);
*/

                int key = 67; //KeyEvent.VK_Z + 33; //123 max
                //key = 1;//46;
                System.out.println(key);
                robot.keyPress(key);
                Thread.sleep(1000);
                robot.keyRelease(key);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
       // }
    }

/*    public static interface MyUser32 extends StdCallLibrary {
        final MyUser32 instance = (MyUser32) Native.loadLibrary ("user32", User32.class);
        HWND FindWindow(String winClass, String title);
        HWND FindWindowExA(HWND hwndParent, HWND childAfter, String className, String windowName);
        HWND FindWindowA(String className, String windowName);
        boolean ShowWindow(HWND hWnd, int nCmdShow);
        boolean SetForegroundWindow(HWND hWnd);

        int PostMessageA(HWND hWnd, int msg, int wParam, int lParam);
        WinDef.LRESULT SendMessage(HWND hWnd, int Msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam);
        int GetClassNameA(HWND hWnd, byte[] lpString, int maxCount);
    }*/
    public static String getWindowText(HWND window) {
        User32 user32 = User32.INSTANCE;
        char[] chars = new char[user32.GetWindowTextLength(window) + 1];
        user32.GetWindowText(window, chars, chars.length);
        String windowName = new String(chars, 0, chars.length - 1);
        return windowName;
    }

    public interface MyUser32 extends StdCallLibrary, WinUser {

        static MyUser32 INSTANCE = (MyUser32) Native.loadLibrary("user32",
                MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter,
                          String lpszClass, String lpszWindow);


        LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);
        LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, String lParam);

        LPARAM GetMessageExtraInfo();

        int MapVirtualKey(int uCode, int uMapType);
        int SetFocus(HWND hWnd);


    }


}
