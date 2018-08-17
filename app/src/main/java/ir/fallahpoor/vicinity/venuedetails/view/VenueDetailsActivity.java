package ir.fallahpoor.vicinity.venuedetails.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.venuedetails.di.DaggerVenueDetailsComponent;
import ir.fallahpoor.vicinity.venuedetails.di.VenueDetailsModule;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenter;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public class VenueDetailsActivity extends MvpActivity<VenueDetailsView, VenueDetailsPresenter> implements VenueDetailsView {

    public static final String KEY_VENUE_ID = "venue_id";

    @Inject
    VenueDetailsPresenter venueDetailsPresenter;
    private LinearLayout contentLayout;
    private RelativeLayout tryAgainLayout;
    private RelativeLayout loadingLayout;
    private TextView errorMessageTextView;
    private Button tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        injectDependencies();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);

        bindViews();

        getPresenter().getVenueDetails(getVenueId());

    }

    private void injectDependencies() {
        DaggerVenueDetailsComponent.builder()
                .venueDetailsModule(new VenueDetailsModule(this))
                .build()
                .inject(this);
    }

    private void bindViews() {
        contentLayout = findViewById(R.id.content_layout);
        tryAgainLayout = findViewById(R.id.try_again_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        errorMessageTextView = findViewById(R.id.error_message_text_view);
        tryAgainButton = findViewById(R.id.try_again_button);
    }

    private String getVenueId() {
        Bundle bundle = getIntent().getExtras();
        return ((bundle == null) ? "" : bundle.getString(KEY_VENUE_ID));
    }

    @NonNull
    @Override
    public VenueDetailsPresenter createPresenter() {
        return venueDetailsPresenter;
    }

    @Override
    public void showLoading() {
        tryAgainLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        errorMessageTextView.setText(errorMessage);
        tryAgainButton.setOnClickListener(view -> getPresenter().getVenueDetails(getVenueId()));
        tryAgainLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
    }

    @Override
    public void showPlace(VenueViewModel venue) {

        contentLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);

        TextView placeNameTextView = findViewById(R.id.venue_name_text_view);
        placeNameTextView.setText(venue.getName());

    }

}
