package com.hystra.entity.mapping;


import javax.persistence.*;
import java.util.Date;

/**
 * @author Syed Muhammad Hassan
 * 7th June, 2023
 */
@Entity
@Table(name="FormCoacheeMapping")
public class FormCoacheeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "coacheeId")
    private int coacheeId;
    @Column(name = "formId")
    private int formId;
    @Column(name = "formName")
    private String formName;
    @Column(name = "UPDATE_BY")
    private String UPDATE_BY;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoacheeId() {
        return coacheeId;
    }

    public void setCoacheeId(int coacheeId) {
        this.coacheeId = coacheeId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getUPDATE_BY() {
        return UPDATE_BY;
    }

    public void setUPDATE_BY(String UPDATE_BY) {
        this.UPDATE_BY = UPDATE_BY;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
