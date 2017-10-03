package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoContactPage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Name", "Surname",
                    "TrueNick", "8(908)778-80-25", "test@mail.ru", 11,
                    "April", 1955, "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().alertOk();
        app.getContactHelper().returnToContactPage();
    }

}
