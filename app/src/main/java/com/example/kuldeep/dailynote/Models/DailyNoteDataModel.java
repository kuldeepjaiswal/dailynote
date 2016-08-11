package com.example.kuldeep.dailynote.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kuldeep on 1/8/2016.
 */
public class DailyNoteDataModel implements Parcelable {
    String Id;
    String Subject;
    String Description;
    String DateTime;

    public DailyNoteDataModel(String id, String subject, String description, String dateTime) {
        Id = id;
        Subject = subject;
        Description = description;
        DateTime = dateTime;
    }

    public DailyNoteDataModel() {
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    protected DailyNoteDataModel(Parcel in) {
        Id = in.readString();
        Subject = in.readString();
        Description = in.readString();
        DateTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Subject);
        dest.writeString(Description);
        dest.writeString(DateTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DailyNoteDataModel> CREATOR = new Parcelable.Creator<DailyNoteDataModel>() {
        @Override
        public DailyNoteDataModel createFromParcel(Parcel in) {
            return new DailyNoteDataModel(in);
        }

        @Override
        public DailyNoteDataModel[] newArray(int size) {
            return new DailyNoteDataModel[size];
        }
    };
}