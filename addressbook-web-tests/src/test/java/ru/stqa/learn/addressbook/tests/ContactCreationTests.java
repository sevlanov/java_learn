package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/stru.jpg");
        ContactData contact = new ContactData().withFirstName("Name").withLastName("Surname")
                .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                .withBmonth("April").withByYear(1955).withGroup("test1").withPhoto(photo);
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test(enabled = false)
    public void testBadContactCreation() {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Name'").withLastName("Surname")
                .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                .withBmonth("April").withByYear(1955).withGroup("test1");
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        contact.withFirstName("Name"); // Изменяем имя в соответствии с логикой приложения: "удаление символа ' при соранении имени"
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}
