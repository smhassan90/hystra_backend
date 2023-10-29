package com.hystra.dal;

public class MonthlySalesReport {
    private String positionCode;
    private String empName;
    private Integer noOfDoctors;
    private Integer noOfDoctorsActive;
    private Integer sale;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getNoOfDoctors() {
        return noOfDoctors;
    }

    public void setNoOfDoctors(Integer noOfDoctors) {
        this.noOfDoctors = noOfDoctors;
    }

    public Integer getNoOfDoctorsActive() {
        return noOfDoctorsActive;
    }

    public void setNoOfDoctorsActive(Integer noOfDoctorsActive) {
        this.noOfDoctorsActive = noOfDoctorsActive;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}
