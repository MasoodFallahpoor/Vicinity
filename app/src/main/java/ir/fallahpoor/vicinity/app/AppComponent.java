package ir.fallahpoor.vicinity.app;

import android.content.Context;

import dagger.Component;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;
import ir.fallahpoor.vicinity.domain.executor.ThreadExecutor;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();
}
