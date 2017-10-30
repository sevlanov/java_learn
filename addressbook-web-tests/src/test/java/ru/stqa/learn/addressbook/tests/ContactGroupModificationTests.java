package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactGroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if ( app.db().groups().size() == 0 ) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname")
                    .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                    .withBmonth("April").withByYear(1955).inGroup(groups.iterator().next()), true);
        }
    }

    @Test
    public void testContactGroupAdd() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();

        Groups groups = app.db().groups();
        GroupData addedGroup = groups.iterator().next();
        try { // Находим группу, которой контакт ещё не принадлежит
            //System.out.println(modifiedContact.getGroups().contains(addedGroup));
            while (modifiedContact.getGroups().contains(addedGroup)) {
                groups = groups.whithout(addedGroup);
                addedGroup = groups.iterator().next();
            }
        } catch (NoSuchElementException ex) { // Если не осталось групп, то создаем новую и запоминаем её
            app.goTo().groupPage();
            addedGroup = new GroupData().withName("testContactGroup");
            app.group().create(addedGroup);
            groups =  app.db().groups();
            addedGroup = addedGroup.withId(groups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        }
        app.goTo().contactPage();
        app.contact().groupAddToContact(modifiedContact, addedGroup);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(modifiedContact.inGroup(addedGroup))));
    }

    @Test
    public void testContactGroupDelete() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        GroupData deletedGroup;
        // Находим контакт c группой
        for (ContactData contact : before) {
            modifiedContact = contact;
            if (modifiedContact.getGroups().size() > 0)  break;
        }
        if (modifiedContact.getGroups().size() == 0) {// Если не осталось групп, то создаем новую и запоминаем её
            app.goTo().groupPage();
            Groups groups = app.db().groups();
            deletedGroup = groups.iterator().next();
            before = before.without(modifiedContact);
            modifiedContact.inGroup(deletedGroup);
            app.goTo().contactPage();
            app.contact().groupAddToContact(modifiedContact, deletedGroup);
            before = before.withAdded(modifiedContact);
        } else {
            deletedGroup = modifiedContact.getGroups().iterator().next();
        }
        app.goTo().contactPage();
        app.contact().groupDeleteFromContact(modifiedContact, deletedGroup);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(modifiedContact.outGroup(deletedGroup))));
    }

}
