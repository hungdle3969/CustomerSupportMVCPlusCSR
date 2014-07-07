package com.hung.le.site;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tickets")
public class TicketWebServiceList {

	private List<Ticket> value;
	
	@XmlElement(name="ticket")
	public List<Ticket> getValue(){
		return this.value;
	}
	
	public void setValue(List<Ticket> value){
		this.value = value;
	}
}
