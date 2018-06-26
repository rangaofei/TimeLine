package io.github.rangaofei.timeline;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineImageView;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;


@TimeLine(name = "BaseModelAdapter", keyLayoutId = "R.layout.item_key", valueLayoutId = "R.layout.item_value")
public class BaseModel implements Parcelable, Comparable<BaseModel> {

    @TimeLineTextView(value = "R.id.key", textColor = "0xFFFF5722")
    public String key;

    @TimeLineTextView(key = false, value = "R.id.value", backGroundColor = "0xFFcddc39")
    public String value;

    @TimeLineImageView(key = false, value = "R.id.iv")
    public int imageViewId;

    public BaseModel() {
    }

    public BaseModel(String key, String value, int imageViewId) {
        this.key = key;
        this.value = value;
        this.imageViewId = imageViewId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "BaseModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    protected BaseModel(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    @Override
    public int compareTo(@NonNull BaseModel o) {
        if (o.key == null) {
            return 1;
        } else if (this.key == null) {
            return -1;
        } else {
            return this.key.compareToIgnoreCase(o.key);
        }

    }
}
