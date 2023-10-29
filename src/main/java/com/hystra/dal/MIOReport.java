package com.hystra.dal;

public class MIOReport {
    private String transactionDate;
    private String providerCode;
    private String providerName;
    private String MIO;
    private String district;
    private String category;
    private String pharmacyCode;
    private String taggedPharmacy;
    private String sales;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getMIO() {
        return MIO;
    }

    public void setMIO(String MIO) {
        this.MIO = MIO;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPharmacyCode() {
        return pharmacyCode;
    }

    public void setPharmacyCode(String pharmacyCode) {
        this.pharmacyCode = pharmacyCode;
    }

    public String getTaggedPharmacy() {
        return taggedPharmacy;
    }

    public void setTaggedPharmacy(String taggedPharmacy) {
        this.taggedPharmacy = taggedPharmacy;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
