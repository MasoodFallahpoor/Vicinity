package ir.fallahpoor.vicinity.venues.model;

public class VenueViewModel {

    private String id;
    private String name;
    private LocationViewModel location;

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

    public LocationViewModel getLocation() {
        return location;
    }

    public void setLocation(LocationViewModel location) {
        this.location = location;
    }

}
