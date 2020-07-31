package com.kun.common.net;

import androidx.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

  @NonNull
  Scheduler computation();

  @NonNull
  Scheduler io();

  @NonNull
  Scheduler ui();

  @NonNull
  <T> ObservableTransformer<T, T> applySchedulers();

  @NonNull
  <T> ObservableTransformer<T, T> applySchedulersToIO();

  @NonNull
  <T> ObservableTransformer<T, T> applySchedulersToMain();
}
