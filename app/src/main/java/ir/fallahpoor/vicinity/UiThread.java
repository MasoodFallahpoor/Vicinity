package ir.fallahpoor.vicinity;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;

public class UiThread implements PostExecutionThread {

    @Inject
    public UiThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
