package com.example.lab3bhanudahal.Model;

public class SalaryDetails {

private int Sid;
private String Name;

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
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

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
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

    private String Email;
private String  Phone;

    public SalaryDetails(int sid, String name, String email, String phone, int salary, String month, int eid) {
        Sid = sid;
        Name = name;
        Email = email;
        Phone = phone;
        Salary = salary;
        Month = month;
        Eid = eid;
    }



    private int Salary;
private String Month;
private int Eid;


}
