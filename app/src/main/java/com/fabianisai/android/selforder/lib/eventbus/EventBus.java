package com.fabianisai.android.selforder.lib.eventbus;

/**
 * Created by fabianisai on 6/12/16.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
