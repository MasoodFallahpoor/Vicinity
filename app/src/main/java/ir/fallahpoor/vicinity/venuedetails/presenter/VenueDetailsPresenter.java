package ir.fallahpoor.vicinity.venuedetails.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;

public interface VenueDetailsPresenter extends MvpPresenter<VenueDetailsView> {

    void getVenueDetails(String id);

}
