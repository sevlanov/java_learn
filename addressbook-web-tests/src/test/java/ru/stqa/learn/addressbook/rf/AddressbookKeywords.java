package ru.stqa.learn.addressbook.rf;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.appmanager.ApplicationManager;
import ru.stqa.learn.addressbook.model.GroupData;

public class AddressbookKeywords {

    private ApplicationManager app;

    public void initApplicationManager() throws Exception{
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.init();

    }

    public void stopApplicationManager() throws Exception{
        app.stop();
        app = null;
    }

    public int getGroupCount() {
        app.goTo().groupPage();
        return app.group().count();
    }

    public void createGroup(String name, String header, String footer) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(name).withHeader(header).withFooter(footer));
    }

/*
    Can Create Group With Valid Data
    ${old_count} =    Get Group Count
    Create Group    test name    test header    test footer
    ${new_count} =    Get Group Count
    ${expected_count} =    Evaluate    ${old_count} + 1
    Shold Be Equal As Integers    ${new_count}    ${expected_count}
*/

}
