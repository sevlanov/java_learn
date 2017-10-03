package ru.stqa.learn.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.learn.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("nickname"), contactData.getNickName());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        click(By.xpath("//select[@name='bday']//option[.=\"" + contactData.getBday() + "\"]"));
        click(By.xpath("//select[@name='bmonth']//option[.=\"" + contactData.getBmonth() + "\"]"));
        type(By.name("byear"), String.valueOf(contactData.getByYear()));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void returnToContactPage() {
        click(By.linkText("home"));
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value=\"Delete\"]"));
    }

    public void alertOk() {
        alertAccept();
    }

    public void initContactModification() {
        click(By.xpath("//a[./img[@title=\"Edit\"]]"));
    }

    public void submitContactModification() {
        click(By.xpath("//input[@value=\"Update\"]"));
    }
}
