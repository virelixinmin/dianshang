package com.bw.project_demo.data.beans;

public class RegisterBean {
        private String message;
        private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterBean(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
