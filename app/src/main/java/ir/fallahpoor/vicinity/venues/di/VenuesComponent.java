package ir.fallahpoor.vicinity.venues.di;

import dagger.Component;
import ir.fallahpoor.vicinity.app.AppComponent;
import ir.fallahpoor.vicinity.venues.view.VenuesActivity;

@Component(dependencies = {AppComponent.class}, modules = VenuesModule.class)
public interface VenuesComponent {
    void inject(VenuesActivity venuesActivity);
}
