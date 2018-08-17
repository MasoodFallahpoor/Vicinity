package ir.fallahpoor.vicinity.venues.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.domain.model.Location;
import ir.fallahpoor.vicinity.domain.model.Venue;

public class VenuesDataMapper {

    @Inject
    public VenuesDataMapper() {
    }

    public List<VenueViewModel> transformVenues(List<Venue> venues) {

        List<VenueViewModel> venueViewModels;

        if (venues != null && !venues.isEmpty()) {
            venueViewModels = new ArrayList<>();
            for (Venue venue : venues) {
                venueViewModels.add(transformVenue(venue));
            }
        } else {
            venueViewModels = Collections.emptyList();
        }

        return venueViewModels;

    }

    private VenueViewModel transformVenue(Venue venue) {

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
