package es.iessaladillo.alex.adm_pr07_fragments.local.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import es.iessaladillo.alex.adm_pr07_fragments.utils.ValidationUtils;

public class User implements Parcelable {
    private long id;
    private Avatar avatar;
    private String name;
    private String email;
    private int phoneNumber;
    private String address;
    private String web;
    private static int counterId;

    public User(@NonNull Avatar avatar, String name, String email, int phoneNumber, String address, String web) {
        id = counterId;
        counterId++;
        this.avatar = avatar;
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setAddress(address);
        setWeb(web);
    }

    public User() {
        id = counterId;
        counterId++;
    }

    protected User(Parcel in) {
        id = in.readLong();
        avatar = in.readParcelable(Avatar.class.getClassLoader());
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readInt();
        address = in.readString();
        web = in.readString();
    }

    public long getId() {
        return id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.isEmpty()) {
            this.name = name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (ValidationUtils.isValidEmail(email)) {
            this.email = email;
        }
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        if (ValidationUtils.isValidPhone(String.valueOf(phoneNumber))) {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.isEmpty()) {
            this.address = address;
        }
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        if (ValidationUtils.isValidUrl(web)) {
            this.web = web;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(avatar, flags);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(phoneNumber);
        dest.writeString(address);
        dest.writeString(web);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
