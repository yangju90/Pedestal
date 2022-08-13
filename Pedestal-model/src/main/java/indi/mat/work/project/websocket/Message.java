package indi.mat.work.project.websocket;

import java.util.List;

/**
 * @author Mat
 * @version : Message, v 0.1 2022-06-27 21:53 Yang
 */
public class Message {
    private boolean isSystem;
    private String toName;
    private List<String> users;
    private String message;

    public Message(){}

    public Message(boolean isSystem, String toName, List<String> users) {
        this.isSystem = isSystem;
        this.toName = toName;
        this.users = users;
    }

    public Message(boolean isSystem, String toName, String message) {
        this.isSystem = isSystem;
        this.toName = toName;
        this.message = message;
    }


    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
