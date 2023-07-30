package com.hystra.dal;

public class DashboardNumbers {
    private String name;
    private int number;
    /*
    APIType 1 means Sales Target
    APIType 2 means Achievement
    APIType 3 means totalProviders
    APIType 4 means activeProviders
     */
    private int APIType;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAPIType() {
        return APIType;
    }

    public void setAPIType(int APIType) {
        this.APIType = APIType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
