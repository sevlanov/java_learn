package ru.stqa.learn.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xstream.fromXML(new File("src/test/resources/contacts.xml"));
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        String json = "";
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(new FileReader(new File("src/test/resources/contacts.json")),
                new TypeToken<List<ContactData>>(){}.getType());
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        String json = "";
        Gson gson = new Gson();
        List<GroupData> groups = gson.fromJson(new FileReader(new File("src/test/resources/groups.json")),
                new TypeToken<List<GroupData>>(){}.getType());
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void ensurePrecondition() {
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
            app.goTo().groupPage();
                app.group().create(new GroupData().withName("group").withHeader("headerTest").withFooter("footerTest"));
        }
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        Groups groups = app.db().groups();
        app.goTo().contactPage();
        Contacts before = app.db().contacts();
        File photo = new File("src/test/resources/stru.jpg");
        app.contact().create(contact.withPhoto(photo).inGroup(groups.iterator().next()), true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }

    @Test(enabled = false)
    public void testBadContactCreation() {
        Groups groups = app.db().groups();
        app.goTo().contactPage();
        Contacts before = app.db().contacts();
        ContactData contact = new ContactData().withFirstName("Name'").withLastName("Surname")
                .withNickName("TrueNick").withMobile("8(908)778-80-25").withEmail("test@mail.ru").withBday(11)
                .withBmonth("April").withByYear(1955).inGroup(groups.iterator().next());
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        contact.withFirstName("Name"); /* Изменяем имя в соответствии с логикой приложения: "удаление символа ' при соранении имени" */
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}
