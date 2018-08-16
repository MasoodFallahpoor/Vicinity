package ir.fallahpoor.vicinity.venues.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import ir.fallahpoor.vicinity.venues.view.VenuesView;

public interface VenuesPresenter extends MvpPresenter<VenuesView> {

    void getVenuesAround(double latitude, double longitude);

}
