package com.autolink.dvr.common.init;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes.dex */
public final class SchedulerSuppress {
    private SchedulerSuppress() {
        throw new IllegalStateException("No instances!");
    }

    public static void SuppressIo() {
        RxJavaPlugins.setIoSchedulerHandler(new IoSuppression(RxJavaPlugins.getIoSchedulerHandler()));
    }

    public static void SuppressCompute() {
        RxJavaPlugins.setComputationSchedulerHandler(new ComputeSuppression(RxJavaPlugins.getComputationSchedulerHandler()));
    }

    public static void SuppressBackground() {
        RxJavaPlugins.setComputationSchedulerHandler(new BackgroundThreadSuppression(RxJavaPlugins.getComputationSchedulerHandler()));
        RxJavaPlugins.setIoSchedulerHandler(new BackgroundThreadSuppression(RxJavaPlugins.getIoSchedulerHandler()));
    }

    public static class BackgroundThreadSuppression extends AbstractSuppression {
        public BackgroundThreadSuppression() {
        }

        public BackgroundThreadSuppression(Function<? super Scheduler, ? extends Scheduler> function) {
            super(function);
        }

        @Override // com.autolink.dvr.common.init.SchedulerSuppress.AbstractSuppression
        boolean shouldJustRunInCurrentThread(Thread thread) {
            String name = thread.getName();
            return name != null && (name.startsWith("RxComputation") || name.startsWith("RxCached"));
        }
    }

    public static class ComputeSuppression extends AbstractSuppression {
        public ComputeSuppression() {
        }

        public ComputeSuppression(Function<? super Scheduler, ? extends Scheduler> function) {
            super(function);
        }

        @Override // com.autolink.dvr.common.init.SchedulerSuppress.AbstractSuppression
        boolean shouldJustRunInCurrentThread(Thread thread) {
            String name = thread.getName();
            return name != null && name.startsWith("RxComputation");
        }
    }

    public static class IoSuppression extends AbstractSuppression {
        public IoSuppression() {
        }

        public IoSuppression(Function<? super Scheduler, ? extends Scheduler> function) {
            super(function);
        }

        @Override // com.autolink.dvr.common.init.SchedulerSuppress.AbstractSuppression
        boolean shouldJustRunInCurrentThread(Thread thread) {
            String name = thread.getName();
            return name != null && name.startsWith("RxCached");
        }
    }

    public static abstract class AbstractSuppression implements Function<Scheduler, Scheduler>, Predicate<Thread> {
        private final Function<? super Scheduler, ? extends Scheduler> previousTransformer;

        abstract boolean shouldJustRunInCurrentThread(Thread thread);

        public AbstractSuppression() {
            this(null);
        }

        public AbstractSuppression(Function<? super Scheduler, ? extends Scheduler> function) {
            this.previousTransformer = function;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Scheduler apply(Scheduler scheduler) throws Throwable {
            if (this.previousTransformer != null) {
                return new ImmediateScheduler(this.previousTransformer.apply(scheduler), this);
            }
            return new ImmediateScheduler(scheduler, this);
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public final boolean test(Thread thread) {
            return shouldJustRunInCurrentThread(thread);
        }
    }
}
