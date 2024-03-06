package com.example.lab3bhanudahal.Model;

public class SalaryDetails {
    int sID;
    String Name;
    String Email;

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public int getEid() {
        return Eid;
    }

    public void setEid(int eid) {
        Eid = eid;
    }

    public SalaryDetails(int sID, String name, String email, String phone, String salary, String month, int eid) {
        this.sID = sID;
        Name = name;
        Email = email;
        Phone = phone;
        Salary = salary;
        Month = month;
        Eid = eid;
    }

    String Phone;
    String Salary;
    String Month;
    int Eid;
}
