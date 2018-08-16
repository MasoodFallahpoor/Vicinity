package ir.fallahpoor.vicinity.venues.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.disposables.Disposable;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.venues.model.VenuesDataMapper;
import ir.fallahpoor.vicinity.venues.view.VenuesView;

public class VenuesPresenterImpl extends MvpBasePresenter<VenuesView> implements VenuesPresenter {

    private GetVenuesUseCase getVenuesUseCase;
    private VenuesDataMapper venuesDataMapper;
    private Disposable disposable;

    public VenuesPresenterImpl(GetVenuesUseCase getVenuesUseCase, VenuesDataMapper venuesDataMapper) {
        this.getVenuesUseCase = getVenuesUseCase;
        this.venuesDataMapper = venuesDataMapper;
    }

    @Override
    public void getVenuesAround(double latitude, double longitude) {

        ifViewAttached(VenuesView::showLoading);

        disposable = getVenuesUseCase.execute(GetVenuesUseCase.Params.forLocation(latitude, longitude))
                .subscribe(
                        venues -> ifViewAttached(view -> {
                            view.hideLoading();
                            view.showVenues(venuesDataMapper.transformVenues(venues));
                        }),
                        throwable -> ifViewAttached(view -> {
                            view.hideLoading();
                            view.showError(throwable.getMessage());
                        })
                );

    }

    @Override
    public void destroy() {
        super.destroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
