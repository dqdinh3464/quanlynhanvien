package com.example.quanlynhanvien;

public class NhanVien {
    private int id;
    private String name;
    private String birthDay;
    private String phongBan;
    private String gender;
    private String workedTime;

    public NhanVien() {

    }

    public NhanVien(int id, String name, String birthDay, String phongBan, String gender, String workedTime) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.phongBan = phongBan;
        this.gender = gender;
        this.workedTime = workedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(String workedTime) {
        this.workedTime = workedTime;
    }
}
