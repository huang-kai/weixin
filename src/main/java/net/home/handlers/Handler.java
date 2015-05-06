package net.home.handlers;

import net.home.pojo.IncomingMessage;

public interface Handler {
    public String handleMsg(IncomingMessage msg);
}
