package ir.fallahpoor.vicinity.venuedetails.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.disposables.Disposable;
import ir.fallahpoor.vicinity.domain.interactors.GetVenueDetailsUseCase;
import ir.fallahpoor.vicinity.venuedetails.model.VenueDetailsDataMapper;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;

public class VenueDetailsPresenterImpl extends MvpBasePresenter<VenueDetailsView> implements VenueDetailsPresenter {

    private GetVenueDetailsUseCase getVenueDetailsUseCase;
    private VenueDetailsDataMapper venueDetailsDataMapper;
    private Disposable disposable;

    public VenueDetailsPresenterImpl(GetVenueDetailsUseCase getVenueDetailsUseCase, VenueDetailsDataMapper venueDetailsDataMapper) {
        this.getVenueDetailsUseCase = getVenueDetailsUseCase;
        this.venueDetailsDataMapper = venueDetailsDataMapper;
    }

    @Override
    public void getVenueDetails(String id) {

        ifViewAttached(VenueDetailsView::showLoading);

        disposable = getVenueDetailsUseCase.execute(GetVenueDetailsUseCase.Params.forVenue(id))
                .subscribe(
                        venue -> ifViewAttached(view -> {
                            view.hideLoading();
                            view.showPlace(venueDetailsDataMapper.transform(venue));
                        }),
                        throwable -> ifViewAttached(view -> {
                            view.hideLoading();
                            view.showError(throwable.getMessage());
                        }));

    }

    @Override
    public void destroy() {
        super.destroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
