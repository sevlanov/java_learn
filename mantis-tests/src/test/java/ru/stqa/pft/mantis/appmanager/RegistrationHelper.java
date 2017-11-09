package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends HelperBase{
    //private final ApplicationManager app;
    //private WebDriver wd;

    public RegistrationHelper(ApplicationManager app) {
        super(app);
        //this.app = app;
      //  wd = app.getDriver();
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "signup_page.php");
        type(By.id("username"), username);
        type(By.id("email-field"), email);
        click(By.cssSelector("input[value='Зарегистрироваться']"));
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//span[contains(text(),'Изменить учетную запись')]"));
    }
}
