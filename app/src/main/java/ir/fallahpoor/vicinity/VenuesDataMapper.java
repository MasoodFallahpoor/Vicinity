package ir.fallahpoor.vicinity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.fallahpoor.vicinity.domain.model.Venue;

public class VenuesDataMapper {

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
        }

        return venueViewModel;

    }

}
