package messaging.models;

import java.io.Serializable;

public class SimpleMessage implements Serializable {
    private String sender;
    private String content;

    public SimpleMessage() {
    }

    public SimpleMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
