package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ChangePasswordHelper extends HelperBase{

    public ChangePasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void loginAdministrator(String username, String password) {
        wd.get(app.getProperty("web.baseUrl"));
        type(By.id("username"), username);
        click(By.cssSelector("input[value='Войти']"));
        type(By.id("password"), password);
        click(By.cssSelector("input[value='Войти']"));
    }

    public void selectUser(String username) {
        click(By.xpath("//span[.=' управление ']"));
        click(By.xpath("//a[.='Управление пользователями']"));
        click(By.xpath("//a[.='" + username + "']"));
        //click(By.xpath("//span[contains(text(),'управление')]"));
        //click(By.xpath("//a[contains(text(),'Управление пользователями')]"));
        //click(By.xpath("//a[contains(text(),'Управление пользователями')]"));
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//span[contains(text(),'Изменить учетную запись')]"));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Сбросить пароль']"));
    }
}
