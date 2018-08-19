package ir.fallahpoor.vicinity.venues.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.disposables.Disposable;
import ir.fallahpoor.vicinity.common.Error;
import ir.fallahpoor.vicinity.common.ExceptionHandler;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.venues.model.VenuesDataMapper;
import ir.fallahpoor.vicinity.venues.view.VenuesView;

public class VenuesPresenterImpl extends MvpBasePresenter<VenuesView> implements VenuesPresenter {

    private static final int MAX_DISTANCE_TO_UPDATE_VENUES = 100;
    private GetVenuesUseCase getVenuesUseCase;
    private VenuesDataMapper venuesDataMapper;
    private ExceptionHandler exceptionHandler;
    private Disposable disposable;
    private double prevLatitude;
    private double prevLongitude;

    public VenuesPresenterImpl(GetVenuesUseCase getVenuesUseCase, VenuesDataMapper venuesDataMapper, ExceptionHandler exceptionHandler) {
        this.getVenuesUseCase = getVenuesUseCase;
        this.venuesDataMapper = venuesDataMapper;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void getVenuesAround(double latitude, double longitude) {

        // If the distance between previous location and current one is less that
        // a threshold then simply do nothing.
        if (distanceInMeters(prevLatitude, prevLongitude, latitude, longitude) <= MAX_DISTANCE_TO_UPDATE_VENUES) {
            return;
        }

        ifViewAttached(VenuesView::showLoading);

        disposable = getVenuesUseCase.execute(GetVenuesUseCase.Params.forLocation(latitude, longitude))
                .subscribe(
                        venues -> {
                            prevLatitude = latitude;
                            prevLongitude = longitude;
                            ifViewAttached(view -> {
                                view.hideLoading();
                                view.showVenues(venuesDataMapper.transformVenues(venues));
                            });
                        },
                        throwable -> ifViewAttached(view -> {
                            Error error = exceptionHandler.parseException(throwable);
                            view.hideLoading();
                            view.showError(error.getMessage());
                        })
                );

    }

    private double distanceInMeters(double latitude1, double longitude1, double latitude2, double longitude2) {

        final int radiusOfEarth = 6371;

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radiusOfEarth * c * 1000;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);

    }

    @Override
    public void destroy() {
        super.destroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
