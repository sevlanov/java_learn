package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test(enabled = false)
    public void testContactModification() {
        app.goTo().gotoContactPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Name", "Surname",
                    "TrueNick", "8(908)778-80-25", "test@mail.ru", 11,
                    "April", 1955, "test1"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() -  1).getId(),"NameEdit", "SurnameEdit",
                "TrueNickEdit", "8(908)778-80-00", "EditTest@mail.ru", 21,
                "July", 1985, null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
