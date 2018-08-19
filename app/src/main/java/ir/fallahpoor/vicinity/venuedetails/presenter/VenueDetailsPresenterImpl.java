package ir.fallahpoor.vicinity.venuedetails.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.disposables.Disposable;
import ir.fallahpoor.vicinity.common.Error;
import ir.fallahpoor.vicinity.common.ExceptionHandler;
import ir.fallahpoor.vicinity.domain.interactors.GetVenueDetailsUseCase;
import ir.fallahpoor.vicinity.venuedetails.model.VenueDetailsDataMapper;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;

public class VenueDetailsPresenterImpl extends MvpBasePresenter<VenueDetailsView> implements VenueDetailsPresenter {

    private GetVenueDetailsUseCase getVenueDetailsUseCase;
    private VenueDetailsDataMapper venueDetailsDataMapper;
    private ExceptionHandler exceptionHandler;
    private Disposable disposable;

    public VenueDetailsPresenterImpl(GetVenueDetailsUseCase getVenueDetailsUseCase, VenueDetailsDataMapper venueDetailsDataMapper,
                                     ExceptionHandler exceptionHandler) {
        this.getVenueDetailsUseCase = getVenueDetailsUseCase;
        this.venueDetailsDataMapper = venueDetailsDataMapper;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void getVenueDetails(String id) {

        ifViewAttached(VenueDetailsView::showLoading);

        disposable = getVenueDetailsUseCase.execute(GetVenueDetailsUseCase.Params.forVenue(id))
                .subscribe(
                        venue -> ifViewAttached(view -> {
                            view.hideLoading();
                            view.showVenue(venueDetailsDataMapper.transform(venue));
                        }),
                        throwable -> ifViewAttached(view -> {
                            Error error = exceptionHandler.parseException(throwable);
                            view.hideLoading();
                            view.showError(error.getMessage());
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
