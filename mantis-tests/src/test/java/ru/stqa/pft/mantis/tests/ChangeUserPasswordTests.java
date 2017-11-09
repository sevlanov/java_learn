package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
        // try find some users(NO Admin)
        // create New user if not found nobody
    }

    @Test
    public void testChangeUserPassword() throws IOException {

        // web: login admin
        // web: open users control page
        // db: select random user => user1
        // web: select user = user1
        // web: reset password
        // mail: select message resetPassword
        // extract link => link1
        // web: goto link = link1
        // web: change password => password1
        // http: assert  login user1, password1

        long now = System.currentTimeMillis();
        String adminLogin = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.adminPassword");

        Users users = app.db().users();
        UserData user = users.iterator().next();
        String resetUsername = user.getUsername();
        String resetPassword = "password";
        String email = user.getEmail();

        app.changePassword().loginAdministrator(adminLogin,   password);
        app.getDriver().manage().window().maximize(); // Открыть окно браузера для тоого, чтобы отобразилось меню слева
                                                      // и не нужно было нажимать кнопку открытия меню
        app.changePassword().selectUser(resetUsername);
        app.changePassword().resetPassword();


        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.changePassword().finish(confirmationLink, resetPassword);

        assertTrue(app.newSession().login(resetUsername, resetPassword));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().delete();
        app.mail().stop();
    }

}
