package messaging.models;

import messaging.models.interfaces.BaseMessage;

import java.io.Serializable;

public class SimpleMessage extends BaseMessage implements Serializable {
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
