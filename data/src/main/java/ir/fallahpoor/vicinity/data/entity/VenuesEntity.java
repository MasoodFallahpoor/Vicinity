package ir.fallahpoor.vicinity.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenuesEntity {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {

        @SerializedName("venues")
        private List<VenueEntity> venues;

        public List<VenueEntity> getVenues() {
            return venues;
        }

        public void setVenues(List<VenueEntity> venues) {
            this.venues = venues;
        }

    }

}
