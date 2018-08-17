package ir.fallahpoor.vicinity.venuedetails.model;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.domain.model.Location;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.venues.model.LocationViewModel;
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
            venueViewModel.setLocation(transformLocation(venue.getLocation()));
        }

        return venueViewModel;

    }

    private LocationViewModel transformLocation(Location location) {

        LocationViewModel locationViewModel = new LocationViewModel();

        if (location != null) {
            locationViewModel.setAddress(location.getAddress());
            locationViewModel.setLatitude(location.getLatitude());
            locationViewModel.setLongitude(location.getLongitude());
        }

        return locationViewModel;

    }

}
