<%--@elvariable id="ticketDatabase" type="java.util.Map<Integer, com.hung.le.site.Ticket>" --%>

<spring:message code="title.ticketList" var="listTitle" />
<template:basic htmlTitle="${listTitle }" bodyTitle="${listTitle }">
	<c:choose>
		<c:when test="${fn:length(tickets) == 0 }">
			<i><spring:message code="message.ticketList.none" /></i>
		</c:when>
		<c:otherwise>
			<c:forEach items="${tickets}" var="ticket">
				<spring:message code="message.ticketList.ticket" />&nbsp;${ticket.id}:
				<a href="<c:url value="/ticket/view/${ticket.id}" />">
				<c:out value="${formatDate:abbreviateString(ticket.subject, 60)}"/>
				</a><br />
				<c:out value="${ticket.customerName }" />&nbsp;
				<spring:message code="message.ticketList.created" />&nbsp;
				<formatDate:formatDate value="${ticket.dateCreated}" type="both"
                                 timeStyle="short" dateStyle="medium" /><br />
                <br />
			</c:forEach>
		</c:otherwise>
	</c:choose>
</template:basic>