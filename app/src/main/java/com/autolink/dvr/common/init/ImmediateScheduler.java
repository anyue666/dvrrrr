package com.autolink.dvr.common.init;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class ImmediateScheduler extends Scheduler {
    private final Scheduler actual;
    private final Predicate<Thread> runInCurrentThread;

    public ImmediateScheduler(Scheduler scheduler, Predicate<Thread> predicate) {
        Objects.requireNonNull(scheduler);
        this.actual = scheduler;
        Objects.requireNonNull(predicate);
        this.runInCurrentThread = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable scheduleDirect(Runnable runnable) {
        if (predicate(this.runInCurrentThread)) {
            return TrampolineScheduler.instance().scheduleDirect(runnable);
        }
        return this.actual.scheduleDirect(runnable);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        if (predicate(this.runInCurrentThread)) {
            return TrampolineScheduler.instance().scheduleDirect(runnable, j, timeUnit);
        }
        return this.actual.scheduleDirect(runnable, j, timeUnit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable schedulePeriodicallyDirect(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (predicate(this.runInCurrentThread)) {
            return TrampolineScheduler.instance().schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
        }
        return this.actual.schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        return new ImmediateWorker(this.actual, this.runInCurrentThread);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        this.actual.start();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        this.actual.shutdown();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public long now(TimeUnit timeUnit) {
        return this.actual.now(timeUnit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public <S extends Scheduler & Disposable> S when(Function<Flowable<Flowable<Completable>>, Completable> function) {
        return (S) this.actual.when(function);
    }

    private static final class ImmediateWorker extends Scheduler.Worker {
        private final Scheduler.Worker actualWorker;
        private final Scheduler.Worker currentThreadWorker = TrampolineScheduler.instance().createWorker();
        private final Predicate<Thread> shouldJustRunInCurrentThread;

        ImmediateWorker(Scheduler scheduler, Predicate<Thread> predicate) {
            this.shouldJustRunInCurrentThread = predicate;
            this.actualWorker = scheduler.createWorker();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            if (ImmediateScheduler.predicate(this.shouldJustRunInCurrentThread)) {
                return this.currentThreadWorker.schedule(runnable);
            }
            return this.actualWorker.schedule(runnable);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (ImmediateScheduler.predicate(this.shouldJustRunInCurrentThread)) {
                return this.currentThreadWorker.schedule(runnable, j, timeUnit);
            }
            return this.actualWorker.schedule(runnable, j, timeUnit);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            if (ImmediateScheduler.predicate(this.shouldJustRunInCurrentThread)) {
                return this.currentThreadWorker.schedulePeriodically(runnable, j, j2, timeUnit);
            }
            return this.actualWorker.schedulePeriodically(runnable, j, j2, timeUnit);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.currentThreadWorker.dispose();
            this.actualWorker.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.actualWorker.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public long now(TimeUnit timeUnit) {
            return this.actualWorker.now(timeUnit);
        }
    }

    static boolean predicate(Predicate<Thread> predicate) {
        try {
            return predicate.test(Thread.currentThread());
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return false;
            } catch (Exception e) {
                RxJavaPlugins.onError(e);
                return false;
            }
        }
    }
}
