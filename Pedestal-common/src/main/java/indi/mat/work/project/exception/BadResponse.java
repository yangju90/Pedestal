package indi.mat.work.project.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mat
 * @version : BadResponse, v 0.1 2022-01-22 19:51 Yang
 */
public class BadResponse {
    private String statusCode;
    private boolean status;
    private String message;

    public BadResponse() {

    }

    public BadResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.status = false;
        this.message = message;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
