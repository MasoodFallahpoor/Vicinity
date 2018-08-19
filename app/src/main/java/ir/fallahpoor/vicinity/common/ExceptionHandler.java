package ir.fallahpoor.vicinity.common;

import android.content.Context;
import android.content.res.Resources;

import java.io.IOException;

import javax.inject.Inject;

import ir.fallahpoor.data.R;

public class ExceptionHandler {

    private Context context;

    @Inject
    public ExceptionHandler(Context context) {
        this.context = context;
    }

    public Error parseException(Throwable throwable) {

        Resources resources = context.getResources();
        Error error;

        if (throwable instanceof IOException) {
            error = new Error(resources.getString(R.string.server_unreachable));
        } else {
            error = new Error(resources.getString(R.string.error_unknown));
        }

        return error;
    }

}
