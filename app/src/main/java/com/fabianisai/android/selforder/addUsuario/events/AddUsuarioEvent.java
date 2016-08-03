package com.fabianisai.android.selforder.addUsuario.events;

/**
 * Created by fabianisai on 7/28/16.
 */

public class AddUsuarioEvent {
    public final static int onSignUpError=0;
    public final static int onSignUpSuccess=1;

    private int eventType;
    private String errorMessage;


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
