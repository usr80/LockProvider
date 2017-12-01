package kreedx.org.provider.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author kreedx
 * @since 2017年12月1日 10:58:28
 * Created by Administrator on 2017/12/1.
 */

public class Info implements Parcelable {
    private int id;
    private String pic;
    private String title;

    public Info(Parcel parcel) {
        id = parcel.readInt();
        pic = parcel.readString();
        title = parcel.readString();
    }

    public Info() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Info(int id, String pic, String title) {
        this.id = id;
        this.pic = pic;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(pic);
        parcel.writeString(title);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Info createFromParcel(Parcel parcel) {
            
            return new Info(parcel);
        }

        @Override
        public Info[] newArray(int i) {
            return new Info[i];
        }
    };
}
