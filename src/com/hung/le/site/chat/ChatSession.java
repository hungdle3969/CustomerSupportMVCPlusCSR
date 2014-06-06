/**
 * 
 */
package com.hung.le.site.chat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * @author admin
 * Server endpoint uses this ChatSession to associate a user requesting a chat with 
 * the support representative who responds. The chat session includes the opening message
 * and a log of all messages sent during the chat.
 */
public class ChatSession {
	
	private long sessionId;
    private String customerUsername;
    private Session customer;
    private String representativeUsername;
    private Session representative;
    private Consumer<Session> onRepresentativeJoin;
    private ChatMessage creationMessage;

    public long getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(long sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getCustomerUsername()
    {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername)
    {
        this.customerUsername = customerUsername;
    }

    public Session getCustomer()
    {
        return customer;
    }

    public void setCustomer(Session customer)
    {
        this.customer = customer;
    }

    public String getRepresentativeUsername()
    {
        return representativeUsername;
    }

    public void setRepresentativeUsername(String representativeUsername)
    {
        this.representativeUsername = representativeUsername;
    }

    public Session getRepresentative()
    {
        return representative;
    }

    public void setRepresentative(Session representative)
    {
        this.representative = representative;
        if(this.onRepresentativeJoin != null)
            this.onRepresentativeJoin.accept(representative);
    }

    public void setOnRepresentativeJoin(Consumer<Session> onRepresentativeJoin)
    {
        this.onRepresentativeJoin = onRepresentativeJoin;
    }

    public ChatMessage getCreationMessage()
    {
        return creationMessage;
    }

    public void setCreationMessage(ChatMessage creationMessage)
    {
        this.creationMessage = creationMessage;
    }
}
