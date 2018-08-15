package ir.fallahpoor.vicinity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class VenuesActivity extends MvpActivity<VenuesView, VenuesPresenter> implements VenuesView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);
    }

    @NonNull
    @Override
    public VenuesPresenter createPresenter() {
        return new VenuesPresenterImpl();
    }

}
