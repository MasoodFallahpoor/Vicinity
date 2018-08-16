package ir.fallahpoor.vicinity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;

public class VenuesActivity extends MvpActivity<VenuesView, VenuesPresenter> implements VenuesView {

    private RecyclerView venuesRecyclerView;
    private RelativeLayout tryAgainLayout;
    private RelativeLayout loadingLayout;
    private TextView errorMessageTextView;
    private Button tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        bindViews();

        getPresenter().getVenuesAround(35.734410, 51.394560);

    }

    private void bindViews() {
        venuesRecyclerView = findViewById(R.id.venues_recycler_view);
        tryAgainLayout = findViewById(R.id.try_again_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        errorMessageTextView = findViewById(R.id.error_message_text_view);
        tryAgainButton = findViewById(R.id.try_again_button);
    }

    @NonNull
    @Override
    public VenuesPresenter createPresenter() {
        VenuesRepository venuesRepository = new VenuesRepositoryImpl(new WebServiceFactory(), new VenuesEntityDataMapper());
        GetVenuesUseCase getVenuesUseCase = new GetVenuesUseCase(venuesRepository, new JobExecutor(), new UiThread());
        return new VenuesPresenterImpl(getVenuesUseCase, new VenuesDataMapper());
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
        tryAgainButton.setOnClickListener(view -> getPresenter().getVenuesAround(35.734410, 51.394560));
        tryAgainLayout.setVisibility(View.VISIBLE);
        venuesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showVenues(List<VenueViewModel> venues) {

        tryAgainLayout.setVisibility(View.GONE);
        venuesRecyclerView.setVisibility(View.VISIBLE);

        venuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        VenuesAdapter venuesAdapter = new VenuesAdapter(this, venues);
        venuesRecyclerView.setAdapter(venuesAdapter);

    }

}
