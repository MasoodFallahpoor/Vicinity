package ir.fallahpoor.vicinity.venues.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import ir.fallahpoor.vicinity.BuildConfig;
import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.UiThread;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.Database;
import ir.fallahpoor.vicinity.data.repository.cache.VenuesCache;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;
import ir.fallahpoor.vicinity.venues.model.VenuesDataMapper;
import ir.fallahpoor.vicinity.venues.presenter.VenuesPresenter;
import ir.fallahpoor.vicinity.venues.presenter.VenuesPresenterImpl;

public class VenuesActivity extends MvpActivity<VenuesView, VenuesPresenter> implements VenuesView {

    private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 1000;
    private static final String TAG = "@@@@@@";

    private RecyclerView venuesRecyclerView;
    private RelativeLayout tryAgainLayout;
    private RelativeLayout loadingLayout;
    private TextView errorMessageTextView;
    private Button tryAgainButton;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        bindViews();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void bindViews() {
        venuesRecyclerView = findViewById(R.id.venues_recycler_view);
        tryAgainLayout = findViewById(R.id.try_again_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        errorMessageTextView = findViewById(R.id.error_message_text_view);
        tryAgainButton = findViewById(R.id.try_again_button);
    }

    @Override
    public void onStart() {

        super.onStart();

        if (isAccessFineLocationPermissionGranted()) {
            getLastLocation();
        } else {
            requestPermission();
        }

    }

    private boolean isAccessFineLocationPermissionGranted() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return (permissionState == PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        this.lastLocation = location;
                        Log.d(TAG, "Location: " + location.getLatitude() + "," + location.getLongitude());
                        getPresenter().getVenuesAround(location.getLatitude(), location.getLongitude());
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), R.string.location_disabled, Snackbar.LENGTH_INDEFINITE)
                                .show();
                    }
                });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE_ACCESS_FINE_LOCATION);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted.");
                getLastLocation();
            } else {
                // Permission is denied either temporarily or permanently.
                Snackbar.make(findViewById(android.R.id.content), R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, view -> launchAppSettings())
                        .show();
            }
        }

    }

    private void launchAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        startActivity(intent);
    }

    @NonNull
    @Override
    public VenuesPresenter createPresenter() {
        VenuesDao venuesDao = Database.getDatabase(this).venuesDao();
        VenuesCache venuesCache = new VenuesCache(venuesDao);
        VenuesRepository venuesRepository = new VenuesRepositoryImpl(venuesCache, new WebServiceFactory(), new VenuesEntityDataMapper());
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
        tryAgainButton.setOnClickListener(view -> {
            if (lastLocation != null)
                getPresenter().getVenuesAround(lastLocation.getLatitude(), lastLocation.getLongitude());
        });
        tryAgainLayout.setVisibility(View.VISIBLE);
        venuesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showVenues(List<VenueViewModel> venues) {

        tryAgainLayout.setVisibility(View.GONE);
        venuesRecyclerView.setVisibility(View.VISIBLE);

        venuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        venuesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        VenuesAdapter venuesAdapter = new VenuesAdapter(this, venues);
        venuesRecyclerView.setAdapter(venuesAdapter);

    }

}
