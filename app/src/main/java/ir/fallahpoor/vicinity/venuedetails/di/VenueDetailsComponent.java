package ir.fallahpoor.vicinity.venuedetails.di;

import dagger.Component;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsActivity;

@Component(modules = VenueDetailsModule.class)
public interface VenueDetailsComponent {
    void inject(VenueDetailsActivity venueDetailsActivity);
}
