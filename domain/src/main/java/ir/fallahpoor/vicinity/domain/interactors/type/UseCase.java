package ir.fallahpoor.vicinity.domain.interactors.type;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;
import ir.fallahpoor.vicinity.domain.executor.ThreadExecutor;

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Single<T> buildUseCaseObservable(Params params);

    public Single<T> execute(Params params) {
        return buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

}
