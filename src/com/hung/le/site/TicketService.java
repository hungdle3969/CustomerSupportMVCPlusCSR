package com.hung.le.site;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface TicketService {

	@NotNull
	List<Ticket> getAllTickets();
	
	Ticket getTicket(@Min(value = 1L, message = "{validate.ticketService.getTicket.id}") long id);
	void save(@NotNull(message="{validate.ticketService.save.ticket}") @Valid Ticket ticket);
	void deleteTicket(long id);
}
