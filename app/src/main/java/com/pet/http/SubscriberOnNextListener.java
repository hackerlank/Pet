package com.pet.http;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
