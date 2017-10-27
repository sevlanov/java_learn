package ru.stqa.learn.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xstream.fromXML(new File("src/test/resources/groups.xml"));
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        String json = "";
        Gson gson = new Gson();
        List<GroupData> groups = gson.fromJson(new FileReader(new File("src/test/resources/groups.json")),
                new TypeToken<List<GroupData>>(){}.getType());
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }


    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData group) {
        Groups before = app.db().groups();
        app.goTo().groupPage();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after =  app.db().groups();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyGroupListInUI();
    }

    @Test(enabled = false)
    public void testBadGroupCreation() {
        Groups before = app.db().groups();
        app.goTo().groupPage();
        GroupData group = new GroupData().withName("test'");
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after =  app.db().groups();
        assertThat(after, equalTo(before));
        verifyGroupListInUI();
    }
}
