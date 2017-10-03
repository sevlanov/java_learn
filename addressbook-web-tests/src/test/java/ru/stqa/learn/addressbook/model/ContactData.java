package ru.stqa.learn.addressbook.model;

public class ContactData {

    private String firstName;
    private String lastName;
    private String nickName;
    private String mobile;
    private String email;
    private int bday;
    private String bmonth;
    private int byYear;
    private String group;

    public ContactData(String firstName, String lastName, String nickName, String mobile, String email, int bday,
                       String bmonth, int byYear, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.mobile = mobile;
        this.email = email;
        this.bday = bday;
        this.bmonth = bmonth;
        this.byYear = byYear;
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public int getBday() {
        return bday;
    }

    public int getByYear() {
        return byYear;
    }

    public String getBmonth() {
        return bmonth;
    }

    public String getGroup() {
        return group;
    }
}
