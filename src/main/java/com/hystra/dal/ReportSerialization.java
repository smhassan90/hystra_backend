package com.hystra.dal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hystra.entity.base.TrainingReport;

import java.lang.reflect.Type;

public class ReportSerialization implements JsonSerializer<TrainingReport> {
    @Override
    public JsonElement serialize(TrainingReport trainingReport, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.add("ID", jsonSerializationContext.serialize(trainingReport.getId()));
        object.add("trainerType", jsonSerializationContext.serialize(trainingReport.getTrainerType()));
        object.add("trainingTheme", jsonSerializationContext.serialize(trainingReport.getTrainingTheme()));
        object.add("trainingVenue", jsonSerializationContext.serialize(trainingReport.getTrainingVenue()));
        object.add("fromDate", jsonSerializationContext.serialize(trainingReport.getFromDate()));
        object.add("toDate", jsonSerializationContext.serialize(trainingReport.getToDate()));
        object.add("participantName", jsonSerializationContext.serialize(trainingReport.getParticipantName()));
        object.add("gender", jsonSerializationContext.serialize(trainingReport.getGender()));
        object.add("designation", jsonSerializationContext.serialize(trainingReport.getDesignation()));
        object.add("district", jsonSerializationContext.serialize(trainingReport.getDistrict()));
        object.add("tehsil", jsonSerializationContext.serialize(trainingReport.getTehsil()));
        object.add("facilityName", jsonSerializationContext.serialize(trainingReport.getFacilityName()));
        object.add("contactNumber", jsonSerializationContext.serialize(trainingReport.getContactNumber()));
        object.add("nationalIDCard", jsonSerializationContext.serialize(trainingReport.getNationalIDCard()));
        object.add("preScore", jsonSerializationContext.serialize(trainingReport.getPreScore()));
        object.add("postScore", jsonSerializationContext.serialize(trainingReport.getPostScore()));
        object.add("improvement", jsonSerializationContext.serialize(trainingReport.getImprovement()));
        object.add("organizedBy", jsonSerializationContext.serialize(trainingReport.getOrganizedBy()));


        return object;
    }
}
