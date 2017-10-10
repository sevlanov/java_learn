package ru.stqa.learn.addressbook.model;

public class ContactData {
    private int id;
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
        this.id = Integer.MAX_VALUE;
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


    public ContactData(int id, String firstName, String lastName, String nickName, String mobile, String email, int bday,
                       String bmonth, int byYear, String group) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
