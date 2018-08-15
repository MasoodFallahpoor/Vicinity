package ir.fallahpoor.vicinity;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface VenuesPresenter extends MvpPresenter<VenuesView> {

    void getVenuesAround(double latitude, double longitude);

}
