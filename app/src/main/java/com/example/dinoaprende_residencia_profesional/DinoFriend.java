package com.example.dinoaprende_residencia_profesional;

import android.os.Parcel;
import android.os.Parcelable;

public class DinoFriend implements Parcelable {
    private int id;
    private String dinoName;
    private String dinoSpecie;
    private String dinoPeriod;
    private String dinoDiet;
    private String dinoTemperament;
    private String dinoDescription;
    private String dinoPhoto;
    private int dinoScore;

    public DinoFriend() {
    }

    public DinoFriend(String dinoName, String dinoSpecie, String dinoPeriod, String dinoDiet,
                    String dinoTemperament, String dinoDescription, String dinoPhoto, int dinoScore) {
        this.dinoName = dinoName;
        this.dinoSpecie = dinoSpecie;
        this.dinoPeriod = dinoPeriod;
        this.dinoDiet = dinoDiet;
        this.dinoTemperament = dinoTemperament;
        this.dinoDescription = dinoDescription;
        this.dinoPhoto = dinoPhoto;
        this.dinoScore = dinoScore;
    }

    protected DinoFriend(Parcel in) {
        id = in.readInt();
        dinoName = in.readString();
        dinoSpecie = in.readString();
        dinoPeriod = in.readString();
        dinoDiet = in.readString();
        dinoTemperament = in.readString();
        dinoDescription = in.readString();
        dinoPhoto = in.readString();
        dinoScore = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(dinoName);
        dest.writeString(dinoSpecie);
        dest.writeString(dinoPeriod);
        dest.writeString(dinoDiet);
        dest.writeString(dinoTemperament);
        dest.writeString(dinoDescription);
        dest.writeString(dinoPhoto);
        dest.writeInt(dinoScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DinoFriend> CREATOR = new Creator<DinoFriend>() {
        @Override
        public DinoFriend createFromParcel(Parcel in) {
            return new DinoFriend(in);
        }

        @Override
        public DinoFriend[] newArray(int size) {
            return new DinoFriend[size];
        }
    };

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for dinoName
    public String getDinoName() {
        return dinoName;
    }

    public void setDinoName(String dinoName) {
        this.dinoName = dinoName;
    }

    // Getter and Setter for dinoSpecie
    public String getDinoSpecie() {
        return dinoSpecie;
    }

    public void setDinoSpecie(String dinoSpecie) {
        this.dinoSpecie = dinoSpecie;
    }

    // Getter and Setter for dinoPeriod
    public String getDinoPeriod() {
        return dinoPeriod;
    }

    public void setDinoPeriod(String dinoPeriod) {
        this.dinoPeriod = dinoPeriod;
    }

    // Getter and Setter for dinoDiet
    public String getDinoDiet() {
        return dinoDiet;
    }

    public void setDinoDiet(String dinoDiet) {
        this.dinoDiet = dinoDiet;
    }

    // Getter and Setter for dinoTemperament
    public String getDinoTemperament() {
        return dinoTemperament;
    }

    public void setDinoTemperament(String dinoTemperament) {
        this.dinoTemperament = dinoTemperament;
    }

    // Getter and Setter for dinoDescription
    public String getDinoDescription() {
        return dinoDescription;
    }

    public void setDinoDescription(String dinoDescription) {
        this.dinoDescription = dinoDescription;
    }

    // Getter and Setter for dinoPhoto
    public String getDinoPhoto() {
        return dinoPhoto;
    }

    public void setDinoPhoto(String dinoPhoto) {
        this.dinoPhoto = dinoPhoto;
    }

    // Getter and Setter for dinoScore
    public int getDinoScore() {
        return dinoScore;
    }

    public void setDinoScore(int dinoScore) {
        this.dinoScore = dinoScore;
    }
}