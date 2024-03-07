package com.example.lab3bhanudahal.Model;

public class EmployeeDetail {
    private  int Eid;
    private String Name;
    private String Phone;
    private  String Email;

    public int getEid() {
        return Eid;
    }

    public void setEid(int eid) {
        Eid = eid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public EmployeeDetail(int eid, String name, String phone, String email, String type) {
        Eid = eid;
        Name = name;
        Phone = phone;
        Email = email;
        Type = type;
    }

    private String Type;




}
