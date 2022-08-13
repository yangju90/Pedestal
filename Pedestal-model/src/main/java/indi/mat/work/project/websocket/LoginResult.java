package indi.mat.work.project.websocket;

/**
 * @author Mat
 * @version : LoginResult, v 0.1 2022-06-27 21:29 Yang
 */
public class LoginResult {
    private String user;
    private boolean flag;
    private String message;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
