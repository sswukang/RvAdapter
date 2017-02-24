package cn.sswukang.example.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 国家解析类
 *
 * @author sswukang on 2017/2/21 9:51
 * @version 1.0
 */
public class Country extends BaseObservable implements Parcelable {
    // 国家id
    private int countryId;
    // 国家编码
    private int countryCode;
    // 国家英文名
    private String countryNameEn;
    // 国家中文名
    private String countryNameCn;
    // 国家英文缩写
    private String ab;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public String getCountryNameCn() {
        return countryNameCn;
    }

    public void setCountryNameCn(String countryNameCn) {
        this.countryNameCn = countryNameCn;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", countryCode=" + countryCode +
                ", countryNameEn='" + countryNameEn + '\'' +
                ", countryNameCn='" + countryNameCn + '\'' +
                ", ab='" + ab + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.countryId);
        dest.writeInt(this.countryCode);
        dest.writeString(this.countryNameEn);
        dest.writeString(this.countryNameCn);
        dest.writeString(this.ab);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.countryId = in.readInt();
        this.countryCode = in.readInt();
        this.countryNameEn = in.readString();
        this.countryNameCn = in.readString();
        this.ab = in.readString();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
