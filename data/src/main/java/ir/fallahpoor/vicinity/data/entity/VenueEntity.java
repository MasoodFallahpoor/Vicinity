package ir.fallahpoor.vicinity.data.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "venue")
public class VenueEntity {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @Embedded
    @SerializedName("location")
    private LocationEntity location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

}
