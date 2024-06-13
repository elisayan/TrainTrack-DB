package model;

public class Person {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String cf;
    private String phone;
    private String address;
    private float totalExspense;
    private String personType;
    private String clientType;

    public Person() {
        this.totalExspense = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotalExspense() {
        return totalExspense;
    }

    public void setTotalExspense(float totalExspense) {
        this.totalExspense = totalExspense;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [email=" + email + ", password=" + password + ", name=" + name + ", surname=" + surname + ", cf="
                + cf + ", phone=" + phone + ", address=" + address + ", totalExspense=" + totalExspense
                + ", personType=" + personType + ", clientType=" + clientType + "]";
    }

}
