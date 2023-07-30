package com.hystra.entity.mapping;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Syed Muhammad Hassan
 * 7th June, 2023
 */
@Entity
@Table(name="TrainingParticipantsMapping")
public class TrainingParticipantsMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "participantId")
    private int participantId;
    @Column(name = "formId")
    private int formId;
    @Column(name = "formName")
    private String formName;
    @Column(name = "preScore")
    private int preScore;
    @Column(name = "postScore")
    private int postScore;
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

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getPreScore() {
        return preScore;
    }

    public void setPreScore(int preScore) {
        this.preScore = preScore;
    }

    public int getPostScore() {
        return postScore;
    }

    public void setPostScore(int postScore) {
        this.postScore = postScore;
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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
