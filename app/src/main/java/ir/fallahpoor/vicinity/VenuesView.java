package ir.fallahpoor.vicinity;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface VenuesView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);

    void showVenues(List<VenueViewModel> venues);

}
