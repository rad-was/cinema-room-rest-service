package rad.cinema.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rad.cinema.seat.Seat;
import rad.cinema.seat.SeatDto;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TicketController {

    private TicketService ticketService;

    @PostMapping(value = "/purchase", produces = "application/json")
    public Object buyTicket(@RequestBody SeatDto seat) throws JsonProcessingException {
        Ticket ticket = ticketService.purchaseTicket(seat);

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("token", ticket.getToken());
        json.put("ticket", ticket.getSeat());

        return new ObjectMapper().writeValueAsString(json);
    }

    @PostMapping(value = "/return", produces = "application/json")
    public Object returnTicket(@RequestBody Ticket returnTicket) throws JsonProcessingException {
        Seat returnedSeat = ticketService.returnTicket(returnTicket.getToken());
        return new ObjectMapper().writeValueAsString(Map.of("returned_ticket", returnedSeat));
    }
}
