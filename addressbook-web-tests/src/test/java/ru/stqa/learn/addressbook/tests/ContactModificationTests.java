package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname")
                        .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                        .withBmonth("April").withByYear(1955).inGroup(groups.iterator().next()), true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        File photo = new File("src/test/resources/stru.jpg");
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("NameEdit").withLastName("SurnameEdit")
                .withNickName("TrueNickEdit").withMobile("8(908)778-80-00").withEmail("EditTest@mail.ru").withBday(21)
                .withBmonth("July").withByYear(1985).withPhoto(photo).withHome("8(908)778-80-01").withWork("8(908)778-80-02");
        app.contact().modify(contact);
        assertEquals(app.contact().count(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.whithout(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }


}
