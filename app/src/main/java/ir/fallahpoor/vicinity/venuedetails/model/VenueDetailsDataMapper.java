package ir.fallahpoor.vicinity.venuedetails.model;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public class VenueDetailsDataMapper {

    @Inject
    public VenueDetailsDataMapper() {
    }

    public VenueViewModel transform(Venue venue) {

        VenueViewModel venueViewModel = new VenueViewModel();

        if (venue != null) {
            venueViewModel.setId(venue.getId());
            venueViewModel.setName(venue.getName());
        }

        return venueViewModel;

    }

}
