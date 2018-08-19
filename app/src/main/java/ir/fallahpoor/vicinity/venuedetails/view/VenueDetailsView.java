package ir.fallahpoor.vicinity.venuedetails.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public interface VenueDetailsView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);

    void showVenue(VenueViewModel venue);

}
