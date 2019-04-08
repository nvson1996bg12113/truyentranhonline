package com.binhtt.truyentranhonline.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 02/08/2017
 */
@Data
public class Story implements Parcelable {
    private String name;
    private String urlImage;
    private String urlLink;
    private String description;
    private String reads;
    private String numberChap;
    private String author;
    private String time;
    private boolean isSelect;

    public Story() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.urlImage);
        dest.writeString(this.urlLink);
        dest.writeString(this.description);
        dest.writeString(this.reads);
        dest.writeString(this.numberChap);
        dest.writeString(this.author);
        dest.writeString(this.time);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected Story(Parcel in) {
        this.name = in.readString();
        this.urlImage = in.readString();
        this.urlLink = in.readString();
        this.description = in.readString();
        this.reads = in.readString();
        this.numberChap = in.readString();
        this.author = in.readString();
        this.time = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
