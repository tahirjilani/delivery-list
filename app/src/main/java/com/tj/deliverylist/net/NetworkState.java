package com.tj.deliverylist.net;


public class NetworkState {

    public enum Status{
        STATE_RUNNING,
        STATE_SUCCESS,
        STATE_FAILED
    }


    private final Status status;
    private final String message;

    public static final NetworkState DONE;
    public static final NetworkState LOADING;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.message = msg;
    }

    static {
        DONE = new NetworkState(Status.STATE_RUNNING,"Success");
        LOADING = new NetworkState(Status.STATE_SUCCESS,"Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
