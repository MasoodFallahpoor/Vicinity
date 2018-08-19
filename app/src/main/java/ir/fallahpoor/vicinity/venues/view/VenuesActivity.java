package ir.fallahpoor.vicinity.venues.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.BuildConfig;
import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.app.App;
import ir.fallahpoor.vicinity.databinding.ActivityVenuesBinding;
import ir.fallahpoor.vicinity.venues.di.DaggerVenuesComponent;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;
import ir.fallahpoor.vicinity.venues.presenter.VenuesPresenter;

public class VenuesActivity extends MvpActivity<VenuesView, VenuesPresenter> implements VenuesView {

    @Inject
    VenuesPresenter venuesPresenter;

    private static final String TAG = "@@@@@@";
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 1000;
    private static final long UPDATE_INTERVAL_IN_MS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MS = 5000;

    private ActivityVenuesBinding binding;

    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        injectDependencies();

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venues);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setupLocationCallback();

    }

    private void injectDependencies() {
        DaggerVenuesComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .build()
                .inject(this);
    }

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d(TAG, "Location: NULL");
                } else {
                    lastLocation = locationResult.getLastLocation();
                    Log.d(TAG, "Location: " + lastLocation.getLongitude() + "," + lastLocation.getLongitude());
                    getPresenter().getVenuesAround(lastLocation.getLatitude(), lastLocation.getLongitude());
                }
            }
        };
    }

    private LocationRequest getLocationRequest() {

        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(UPDATE_INTERVAL_IN_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MS);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        return locationRequest;

    }

    @Override
    protected void onStart() {

        super.onStart();

        if (isAccessFineLocationPermissionGranted()) {
            checkLocationSettings();
        } else {
            requestPermission();
        }

    }

    private boolean isAccessFineLocationPermissionGranted() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return (permissionState == PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("MissingPermission")
    private void checkLocationSettings() {

        LocationSettingsRequest locationSettingsRequest;
        locationSettingsRequest = getLocationSettingsRequestBuilder().build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            Log.i(TAG, "All location settings are satisfied. Start location updates...");
            startLocationUpdates();
        });

        task.addOnFailureListener(this, e -> {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    Log.d(TAG, "Location settings are not satisfied. Attempting to upgrade location settings");
                    try {
                        ResolvableApiException rae = (ResolvableApiException) e;
                        rae.startResolutionForResult(VenuesActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sie) {
                        Log.d(TAG, "PendingIntent unable to execute request.");
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    String errorMessage = "Location settings are inadequate, and cannot be fixed here.";
                    Log.d(TAG, errorMessage);
                    Toast.makeText(VenuesActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

        });

    }

    private LocationSettingsRequest.Builder getLocationSettingsRequestBuilder() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(getLocationRequest());
        return builder;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, Looper.myLooper());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE_ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted.");
                checkLocationSettings();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.i(TAG, "User made required location settings changes.");
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.i(TAG, "Required location settings changes NOT made.");
                    Snackbar.make(findViewById(android.R.id.content), R.string.location_disabled, Snackbar.LENGTH_INDEFINITE)
                            .show();
                    break;
            }
        }

    }

    @NonNull
    @Override
    public VenuesPresenter createPresenter() {
        return venuesPresenter;
    }

    @Override
    public void showLoading() {
        binding.tryAgain.tryAgainLayout.setVisibility(View.GONE);
        binding.loading.loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loading.loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        binding.tryAgain.errorMessageTextView.setText(errorMessage);
        binding.tryAgain.tryAgainButton.setOnClickListener(view -> {
            if (lastLocation != null) {
                getPresenter().getVenuesAround(lastLocation.getLatitude(), lastLocation.getLongitude());
            }
        });
        binding.tryAgain.tryAgainLayout.setVisibility(View.VISIBLE);
        binding.venuesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showVenues(List<VenueViewModel> venues) {

        binding.tryAgain.tryAgainLayout.setVisibility(View.GONE);
        binding.venuesRecyclerView.setVisibility(View.VISIBLE);

        binding.venuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.venuesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        VenuesAdapter venuesAdapter = new VenuesAdapter(this, venues);
        binding.venuesRecyclerView.setAdapter(venuesAdapter);

    }

}
