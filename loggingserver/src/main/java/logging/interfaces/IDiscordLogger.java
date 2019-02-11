package logging.interfaces;

import messaging.messages.GuildLogMessage;

public interface IDiscordLogger {
    void logMessage(GuildLogMessage message);
}
