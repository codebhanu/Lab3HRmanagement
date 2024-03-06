package com.example.lab3bhanudahal.Model;

public class EmployeeDetail {
int Eid;
String Name;
String Email;

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

    public EmployeeDetail(int eid, String name, String email, String type) {
        Eid = eid;
        Name = name;
        Email = email;
        Type = type;
    }

    String Type;

}
