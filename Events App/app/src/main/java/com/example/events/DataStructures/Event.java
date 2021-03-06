package com.example.events.DataStructures;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Event {
    @Expose(serialize = false)
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("image")
    private String imageURL;
    @Expose
    @SerializedName("location")
    private String location;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("eventStart_date")
    private String startDate;
    @Expose(serialize = false)
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("eventEnd_date")
    private String endDate;
    @Expose
    @SerializedName("n_participators")
    private int numParticipants;
    @Expose
    @SerializedName("type")
    private String eventType;
    @Expose(serialize = false)
    @SerializedName("owner_id")
    private int ownerID;


    public Event(String name, String imageURL, String location, String description, String startDate, String endDate, int numParticipants, String eventType) {
        this.name = name;
        this.imageURL = imageURL;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numParticipants = numParticipants;
        this.eventType = eventType;
        int currentParticipants = 0;

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

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        if (startDate == null) {
            System.out.println(date);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(startDate);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return dateFormat.format(date);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        if (endDate == null) {

            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(endDate);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return dateFormat.format(date);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


}
