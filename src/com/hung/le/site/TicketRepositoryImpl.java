package com.hung.le.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

	private volatile long TICKET_ID_SEQUENCE = 1;
	
	private Map<Long, Ticket> ticketDatabase = new LinkedHashMap<>();

	@Override
	public List<Ticket> getAll() {
		return new ArrayList<>(this.ticketDatabase.values());
	}

	@Override
	public Ticket get(long id) {
		return this.ticketDatabase.get(id);
	}

	@Override
	public void add(Ticket ticket) {

		ticket.setId(this.getNextTicketId());
		this.ticketDatabase.put(ticket.getId(),ticket);
	}

	@Override
	public void update(Ticket ticket) {
		this.ticketDatabase.put(ticket.getId(), ticket);
		
	}
	
	private synchronized long getNextTicketId(){
		return this.TICKET_ID_SEQUENCE++;
	}
	
	
}
