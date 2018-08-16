package ir.fallahpoor.vicinity.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueDetailsEntity {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("venue")
        @Expose
        private VenueEntity venue;

        public VenueEntity getVenue() {
            return venue;
        }

        public void setVenue(VenueEntity venue) {
            this.venue = venue;
        }

    }

}