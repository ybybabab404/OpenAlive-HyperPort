package io.reactivex.rxjava3.internal.subscribers;

import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: compiled from: QueueDrainSubscriber.java */
/* JADX INFO: loaded from: classes2.dex */
class QueueDrainSubscriberPad3 extends QueueDrainSubscriberPad2 {
    public final AtomicLong requested = new AtomicLong();
}
