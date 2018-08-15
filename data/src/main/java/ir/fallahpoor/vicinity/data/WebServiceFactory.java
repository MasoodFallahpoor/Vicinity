package ir.fallahpoor.vicinity.data;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceFactory {

    public <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder = getRetrofitBuilder();
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    private Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/venues/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
    }

}
