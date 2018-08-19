package ir.fallahpoor.vicinity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvpPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private CompositeDisposable disposables = new CompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

}
