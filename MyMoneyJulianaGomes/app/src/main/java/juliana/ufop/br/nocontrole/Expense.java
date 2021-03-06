package juliana.ufop.br.nocontrole;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Expense implements Parcelable, Serializable {

    private double value;
    private String date;
    private String origin;
    private String pay;
    private String description;

    public Expense(double value, String date, String origin, String pay, String description) {
        this.value = value;
        this.date = date;
        this.origin = origin;
        this.pay = pay;
        this.description = description;
    }

    protected Expense(Parcel in) {
        value = in.readDouble();
        date = in.readString();
        origin = in.readString();
        pay = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(value);
        dest.writeString(date);
        dest.writeString(origin);
        dest.writeString(pay);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String form) {
        this.pay = form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
