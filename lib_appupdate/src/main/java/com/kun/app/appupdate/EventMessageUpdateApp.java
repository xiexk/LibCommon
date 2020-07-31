package com.kun.app.appupdate;

public class EventMessageUpdateApp<T> {
    public int id;

    public EventMessageUpdateApp(int id, T data) {
        this.id = id;
        this.data = data;
    }

    public T data;
}
