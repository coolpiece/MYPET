package com.example.mypet;

public class PetInfo {
    private String name;
    private String pet;
    private String sex;
    private String category;
    private String birth;
    private String profile;

    public PetInfo() { }

    public String getProfile() { return this.profile; }
    public void setProfile(String profile) { this.profile = profile; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getPet() { return this.pet; }
    public void setPet(String pet) { this.pet = pet; }

    public String getSex() { return this.sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public String getBirth() { return this.birth; }
    public void setBirth(String birth) { this.birth = birth; }
}
