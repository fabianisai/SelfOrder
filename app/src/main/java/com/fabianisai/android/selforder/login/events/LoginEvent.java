package com.fabianisai.android.selforder.login.events;

/**
 * Created by fabianisai on 7/20/16.
 */

public class LoginEvent {
    public final static int onSignInError=0;
    public final static int onSignInSuccess=1;
    public final static int onFailedToRecoverSession=2;
    public final static int onSessionActive=3;
    public final static int onNoSession=4;

    private int eventType;
    private String errorMessage;

    private String usrId;



    private String usrEmail;
    private String usrPass;



    private Integer usrSesion;

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

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrPass() {
        return usrPass;
    }

    public void setUsrPass(String usrPass) {
        this.usrPass = usrPass;
    }
    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }
    public Integer getUsrSesion() {
        return usrSesion;
    }

    public void setUsrSesion(Integer usrSesion) {
        this.usrSesion = usrSesion;
    }
}
