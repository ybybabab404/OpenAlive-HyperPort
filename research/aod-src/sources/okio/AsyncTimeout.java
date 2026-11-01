package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import miuix.animation.internal.FolmeCore;

/* JADX INFO: loaded from: classes3.dex */
public class AsyncTimeout extends Timeout {
    public static final long IDLE_TIMEOUT_MILLIS;
    public static final long IDLE_TIMEOUT_NANOS;
    public static AsyncTimeout head;
    public boolean inQueue;
    public AsyncTimeout next;
    public long timeoutAt;

    /* JADX INFO: renamed from: okio.AsyncTimeout$1, reason: invalid class name */
    class AnonymousClass1 implements Sink {
        public final /* synthetic */ AsyncTimeout this$0;
        public final /* synthetic */ Sink val$sink;

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.this$0.enter();
            try {
                try {
                    this.val$sink.close();
                    this.this$0.exit(true);
                } catch (IOException e) {
                    throw this.this$0.exit(e);
                }
            } catch (Throwable th) {
                this.this$0.exit(false);
                throw th;
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            this.this$0.enter();
            try {
                try {
                    this.val$sink.flush();
                    this.this$0.exit(true);
                } catch (IOException e) {
                    throw this.this$0.exit(e);
                }
            } catch (Throwable th) {
                this.this$0.exit(false);
                throw th;
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.this$0;
        }

        public String toString() {
            return "AsyncTimeout.sink(" + this.val$sink + ")";
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            Util.checkOffsetAndCount(buffer.size, 0L, j);
            while (true) {
                long j2 = 0;
                if (j <= 0) {
                    return;
                }
                Segment segment = buffer.head;
                while (j2 < 65536) {
                    j2 += (long) (segment.limit - segment.pos);
                    if (j2 >= j) {
                        j2 = j;
                        break;
                    }
                    segment = segment.next;
                }
                this.this$0.enter();
                try {
                    try {
                        this.val$sink.write(buffer, j2);
                        j -= j2;
                        this.this$0.exit(true);
                    } catch (IOException e) {
                        throw this.this$0.exit(e);
                    }
                } catch (Throwable th) {
                    this.this$0.exit(false);
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: renamed from: okio.AsyncTimeout$2, reason: invalid class name */
    class AnonymousClass2 implements Source {
        public final /* synthetic */ AsyncTimeout this$0;
        public final /* synthetic */ Source val$source;

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.this$0.enter();
            try {
                try {
                    this.val$source.close();
                    this.this$0.exit(true);
                } catch (IOException e) {
                    throw this.this$0.exit(e);
                }
            } catch (Throwable th) {
                this.this$0.exit(false);
                throw th;
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            this.this$0.enter();
            try {
                try {
                    long j2 = this.val$source.read(buffer, j);
                    this.this$0.exit(true);
                    return j2;
                } catch (IOException e) {
                    throw this.this$0.exit(e);
                }
            } catch (Throwable th) {
                this.this$0.exit(false);
                throw th;
            }
        }

        public String toString() {
            return "AsyncTimeout.source(" + this.val$source + ")";
        }
    }

    public static final class Watchdog extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0017, code lost:
        
            r0.timedOut();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r2 = this;
            L0:
                java.lang.Class<okio.AsyncTimeout> r2 = okio.AsyncTimeout.class
                monitor-enter(r2)     // Catch: java.lang.InterruptedException -> L0
                okio.AsyncTimeout r0 = okio.AsyncTimeout.awaitTimeout()     // Catch: java.lang.Throwable -> Lb
                if (r0 != 0) goto Ld
                monitor-exit(r2)     // Catch: java.lang.Throwable -> Lb
                goto L0
            Lb:
                r0 = move-exception
                goto L1b
            Ld:
                okio.AsyncTimeout r1 = okio.AsyncTimeout.head     // Catch: java.lang.Throwable -> Lb
                if (r0 != r1) goto L16
                r0 = 0
                okio.AsyncTimeout.head = r0     // Catch: java.lang.Throwable -> Lb
                monitor-exit(r2)     // Catch: java.lang.Throwable -> Lb
                return
            L16:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> Lb
                r0.timedOut()     // Catch: java.lang.InterruptedException -> L0
                goto L0
            L1b:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> Lb
                throw r0     // Catch: java.lang.InterruptedException -> L0
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.AsyncTimeout.Watchdog.run():void");
        }
    }

    static {
        long millis = TimeUnit.SECONDS.toMillis(60L);
        IDLE_TIMEOUT_MILLIS = millis;
        IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(millis);
    }

    public static AsyncTimeout awaitTimeout() throws InterruptedException {
        AsyncTimeout asyncTimeout = head.next;
        if (asyncTimeout == null) {
            long jNanoTime = System.nanoTime();
            AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
            if (head.next != null || System.nanoTime() - jNanoTime < IDLE_TIMEOUT_NANOS) {
                return null;
            }
            return head;
        }
        long jRemainingNanos = asyncTimeout.remainingNanos(System.nanoTime());
        if (jRemainingNanos > 0) {
            long j = jRemainingNanos / FolmeCore.NANOS_TO_MS;
            AsyncTimeout.class.wait(j, (int) (jRemainingNanos - (FolmeCore.NANOS_TO_MS * j)));
            return null;
        }
        head.next = asyncTimeout.next;
        asyncTimeout.next = null;
        return asyncTimeout;
    }

    public static synchronized boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
        AsyncTimeout asyncTimeout2 = head;
        while (asyncTimeout2 != null) {
            AsyncTimeout asyncTimeout3 = asyncTimeout2.next;
            if (asyncTimeout3 == asyncTimeout) {
                asyncTimeout2.next = asyncTimeout.next;
                asyncTimeout.next = null;
                return false;
            }
            asyncTimeout2 = asyncTimeout3;
        }
        return true;
    }

    public static synchronized void scheduleTimeout(AsyncTimeout asyncTimeout, long j, boolean z) {
        try {
            if (head == null) {
                head = new AsyncTimeout();
                new Watchdog().start();
            }
            long jNanoTime = System.nanoTime();
            if (j != 0 && z) {
                asyncTimeout.timeoutAt = Math.min(j, asyncTimeout.deadlineNanoTime() - jNanoTime) + jNanoTime;
            } else if (j != 0) {
                asyncTimeout.timeoutAt = j + jNanoTime;
            } else {
                if (!z) {
                    throw new AssertionError();
                }
                asyncTimeout.timeoutAt = asyncTimeout.deadlineNanoTime();
            }
            long jRemainingNanos = asyncTimeout.remainingNanos(jNanoTime);
            AsyncTimeout asyncTimeout2 = head;
            while (true) {
                AsyncTimeout asyncTimeout3 = asyncTimeout2.next;
                if (asyncTimeout3 == null || jRemainingNanos < asyncTimeout3.remainingNanos(jNanoTime)) {
                    break;
                    break;
                }
                asyncTimeout2 = asyncTimeout2.next;
            }
            asyncTimeout.next = asyncTimeout2.next;
            asyncTimeout2.next = asyncTimeout;
            if (asyncTimeout2 == head) {
                AsyncTimeout.class.notify();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long jTimeoutNanos = timeoutNanos();
        boolean zHasDeadline = hasDeadline();
        if (jTimeoutNanos != 0 || zHasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, jTimeoutNanos, zHasDeadline);
        }
    }

    public final IOException exit(IOException iOException) {
        return !exit() ? iOException : newTimeoutException(iOException);
    }

    public final void exit(boolean z) throws IOException {
        if (exit() && z) {
            throw newTimeoutException(null);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    public IOException newTimeoutException(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final long remainingNanos(long j) {
        return this.timeoutAt - j;
    }

    public void timedOut() {
    }
}
