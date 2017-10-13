package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname")
                    .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                    .withBmonth("April").withByYear(1955).withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        Assert.assertEquals(app.contact().count(), before.size() - 1);
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.whithout(deletedContact)));
    }



}
