package com.mobileproje;

import android.os.Parcel;
import android.os.Parcelable;

public class Concert implements Parcelable {
    private int id;
    private String name;
    private String place;
    private int price;
    private String image;

    public Concert(int id, String name, String place, int price, String image) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.price = price;
        this.image = image;
    }

    public Concert(String name, String place, int price, String image) {
        this.name = name;
        this.place = place;
        this.price = price;
        this.image = image;
    }

    protected Concert(Parcel in) {
        id = in.readInt();
        name = in.readString();
        place = in.readString();
        price = in.readInt();
        image = in.readString();
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final Creator<Concert> CREATOR = new Creator<Concert>() {
        @Override
        public Concert createFromParcel(Parcel in) {
            return new Concert(in);
        }

        @Override
        public Concert[] newArray(int size) {
            return new Concert[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(place);
        dest.writeInt(price);
        dest.writeString(image);
    }
}
