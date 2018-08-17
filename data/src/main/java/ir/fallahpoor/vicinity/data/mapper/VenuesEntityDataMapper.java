package ir.fallahpoor.vicinity.data.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.domain.model.Venue;

public class VenuesEntityDataMapper {

    @Inject
    public VenuesEntityDataMapper() {
    }

    public List<Venue> transform(List<VenueEntity> venueEntities) {

        List<Venue> venues;

        if (venueEntities != null) {
            venues = new ArrayList<>();
            for (VenueEntity venue : venueEntities) {
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
