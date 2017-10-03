package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {

        app.getNavigationHelper().gotoContactPage();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("NameEdit", "SurnameEdit",
                "TrueNickEdit", "8(908)778-80-00", "EditTest@mail.ru", 21,
                "July", 1985));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();

    }
}
