package cn.sswukang.example.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 国家解析类
 *
 * @author sswukang on 2017/2/21 9:51
 * @version 1.0
 */
public class Country implements Parcelable {
    // 国家id
    private int country_id;
    // 国家编码
    private int country_code;
    // 国家英文名
    private String country_name_en;
    // 国家中文名
    private String country_name_cn;
    // 国家英文缩写
    private String ab;

    public int getCountryId() {
        return country_id;
    }

    public void setCountryId(int country_id) {
        this.country_id = country_id;
    }

    public int getCountryCode() {
        return country_code;
    }

    public void setCountryCode(int country_code) {
        this.country_code = country_code;
    }

    public String getCountryNameEn() {
        return country_name_en;
    }

    public void setCountryNameEn(String country_name_en) {
        this.country_name_en = country_name_en;
    }

    public String getCountryNameCn() {
        return country_name_cn;
    }

    public void setCountryNameCn(String country_name_cn) {
        this.country_name_cn = country_name_cn;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    @Override
    public String toString() {
        return "id:" + country_id + "  " + country_name_cn + "(" + country_code + ")"
                + "  " + country_name_en + "(" + ab + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.country_id);
        dest.writeInt(this.country_code);
        dest.writeString(this.country_name_en);
        dest.writeString(this.country_name_cn);
        dest.writeString(this.ab);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.country_id = in.readInt();
        this.country_code = in.readInt();
        this.country_name_en = in.readString();
        this.country_name_cn = in.readString();
        this.ab = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
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
