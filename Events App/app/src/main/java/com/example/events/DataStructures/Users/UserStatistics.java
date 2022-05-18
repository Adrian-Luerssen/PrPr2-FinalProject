package com.example.events.DataStructures.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStatistics {
    @Expose
    @SerializedName("avg_score")
    private int avgScore;
    @Expose
    @SerializedName("num_comments")
    private int numComments;
    @Expose
    @SerializedName("percentage_commenters_below")
    private float percComments;

    public UserStatistics(int avgScore, int numComments, float percComments) {
        this.avgScore = avgScore;
        this.numComments = numComments;
        this.percComments = percComments;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public float getPercComments() {
        return percComments;
    }

    public void setPercComments(float percComments) {
        this.percComments = percComments;
    }
}
