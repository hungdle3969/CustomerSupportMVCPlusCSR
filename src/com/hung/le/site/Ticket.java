package com.hung.le.site;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.le.site.validation.NotBlank;

@XmlRootElement(namespace="http://companyname.com/xmlns/CustomerSupport", name="ticket")
public class Ticket {
	
	private long id;
	
	@NotBlank(message = "{validate.ticket.customerName}")
	private String customerName;
	
	@NotBlank(message = "{validate.ticket.subject}")
	private String subject;
	
	@NotBlank(message = "{validate.ticket.body}")
	private String body;
	
	@Valid
	@XmlTransient
	private Map<String, Attachment> attachments = new LinkedHashMap<>();
	
	private Instant dateCreated;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@XmlSchemaType(name="dateTime")
	public Instant getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@XmlElement(name="attachment")
	public Collection<Attachment> getAttachments() {
		return this.attachments.values();
	}
	@JsonIgnore
	public Attachment getAttachment(String name){
		return this.attachments.get(name);
	}
	
	public void setAttachments(List<Attachment> attachments) {
		
		for(Attachment attachment : attachments)
            this.addAttachment(attachment);
	}
	@JsonIgnore
	public void addAttachment(Attachment attachment){
		this.attachments.put(attachment.getName(), attachment);
	}
	@XmlTransient
	@JsonIgnore
	public int getNumberOfAttachments(){
		return this.attachments.size();
	}

}
