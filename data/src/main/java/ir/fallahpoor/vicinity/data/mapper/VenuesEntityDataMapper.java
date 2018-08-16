package ir.fallahpoor.vicinity.data.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.entity.VenuesEntity;
import ir.fallahpoor.vicinity.domain.model.Venue;

public class VenuesEntityDataMapper {

    public List<Venue> transform(VenuesEntity venuesEntity) {

        List<Venue> venues;

        if (venuesEntity != null) {
            venues = new ArrayList<>();
            for (VenueEntity venue : venuesEntity.getResponse().getVenues()) {
                venues.add(transformVenue(venue));
            }
        } else {
            venues = Collections.emptyList();
        }

        return venues;

    }

    public Venue transformVenue(VenueEntity venueEntity) {

        Venue venue = new Venue();

        if (venueEntity != null) {
            venue.setId(venueEntity.getId());
            venue.setName(venueEntity.getName());
        }

        return venue;

    }

}
