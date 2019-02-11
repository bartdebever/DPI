package messaging.receivers.interfaces;

import messaging.messages.GuildLogMessage;

public interface IReceiver {
    void receiveMessage(GuildLogMessage message);

    boolean receiveWithReply(GuildLogMessage message);

    boolean reveiveForProcessing(GuildLogMessage message);
}
