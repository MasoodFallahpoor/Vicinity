package ir.fallahpoor.vicinity.venues.di;

import dagger.Component;
import ir.fallahpoor.vicinity.venues.view.VenuesActivity;

@Component(modules = VenuesModule.class)
public interface VenuesComponent {
    void inject(VenuesActivity venuesActivity);
}
