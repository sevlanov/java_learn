package ru.stqa.learn.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname")
                    .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                    .withBmonth("April").withByYear(1955).withGroup("test1").withAddress("test Address"), true);
        }
    }

    @Test
    public void testContactEmail() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}
