package rad.cinema.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rad.cinema.seat.Seat;
import rad.cinema.seat.SeatRepository;
import rad.cinema.ticket.Ticket;
import rad.cinema.ticket.TicketRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    @GetMapping(value = "/seats", produces = "application/json")
    public Room getAvailableSeats() {
        Optional<Room> room = roomService.findRoomById(1);

        if (room.isPresent()) {
            Room availableSeatsRoom = new Room();
            availableSeatsRoom.setTotalRows(room.get().getTotalRows());
            availableSeatsRoom.setTotalColumns(room.get().getTotalColumns());

            List<Seat> availableSeats = room.get().getAvailableSeats()
                    .stream()
                    .filter(seat -> !seat.getIsTaken())
                    .collect(Collectors.toList());

            availableSeatsRoom.setAvailableSeats(availableSeats);

            return availableSeatsRoom;
        }
        throw new RuntimeException();
    }

    @PostMapping(value = "/purchase", produces = "application/json")
    public Object bookSeat(@RequestBody Seat seat) throws JsonProcessingException {
        Optional<Room> roomOptional = roomService.findRoomById(1);

        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            List<Seat> seats = room.getAvailableSeats();

            if (seats.contains(seat)) {
                int i = seats.indexOf(seat);
                Seat seatToBook = seats.get(i);

                if (seatToBook.getIsTaken()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has already been purchased!");
                }

                seatToBook.setIsTaken(true);
                room.setAvailableSeats(seats);
                roomService.updateRoom(room);

                Ticket ticket = new Ticket(UUID.randomUUID(), seatToBook);
                ticketRepository.save(ticket);

                Map<String, Object> json = new LinkedHashMap<>();
                json.put("token", ticket.getToken());
                json.put("ticket", seatToBook);

                return new ObjectMapper().writeValueAsString(json);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!");
        }
    }

    @PostMapping(value = "/return", produces = "application/json")
    public Object returnTicket(@RequestBody Ticket returnTicket) throws JsonProcessingException {
        Optional<Ticket> ticketOptional = ticketRepository.findById(returnTicket.getToken());
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticketRepository.delete(ticket);

            Optional<Room> roomOptional = roomService.findRoomById(1);
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                List<Seat> seats = room.getAvailableSeats();
                int index = seats.indexOf(ticket.getSeat());
                seats.get(index).setIsTaken(false);
                room.setAvailableSeats(seats);
                roomService.updateRoom(room);
            }

            return new ObjectMapper().writeValueAsString(Map.of("returned_ticket", ticket.getSeat()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token!");
        }
    }
}
