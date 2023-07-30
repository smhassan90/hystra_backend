package com.hystra.dal;

import com.hystra.entity.base.TrainingReport;

import java.util.ArrayList;

public class Report {
    ArrayList<TrainingReport> trainingReports = new ArrayList<>();

    public ArrayList<TrainingReport> getTrainingReports() {
        return trainingReports;
    }

    public void setTrainingReports(ArrayList<TrainingReport> trainingReports) {
        this.trainingReports = trainingReports;
    }
}