package com.bw.project_demo.ui.fragment.xiangqing;

public class SuccessBean {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SuccessBean(String status, String message) {

        this.status = status;
        this.message = message;
    }

}
