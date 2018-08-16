package ir.fallahpoor.vicinity.venues.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public interface VenuesView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);

    void showVenues(List<VenueViewModel> venues);

}
