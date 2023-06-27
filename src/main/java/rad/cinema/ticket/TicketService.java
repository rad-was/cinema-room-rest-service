package rad.cinema.ticket;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rad.cinema.room.Room;
import rad.cinema.room.RoomService;
import rad.cinema.seat.Seat;
import rad.cinema.seat.SeatDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;
    private RoomService roomService;

    public void saveTicket(UUID token, Seat seat) {
        ticketRepository.save(new Ticket(token, seat));
    }

    public Ticket purchaseTicket(SeatDto seatDto) {
        Seat seat = new Seat(seatDto.getRow(), seatDto.getColumn());

        Room room = roomService.findRoomById(1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!"));

        List<Seat> availableSeats = roomService.findAvailableSeats(room);

        if (availableSeats.contains(seat)) {
            int i = availableSeats.indexOf(seat);
            Seat seatToBook = availableSeats.get(i);
            seatToBook.setIsTaken(true);

            UUID ticketToken = UUID.randomUUID();
            saveTicket(ticketToken, seatToBook);

            return new Ticket(ticketToken, seatToBook);
        } else if (room.getSeats().contains(seat)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has already been purchased!");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
        }
    }

    public Seat returnTicket(UUID token) {
        Room room = roomService.findRoomById(1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!"));
        List<Seat> seats = room.getSeats();

        Ticket ticket = ticketRepository.findById(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token!"));

        ticketRepository.delete(ticket);

        Optional<Room> roomOptional = roomService.findRoomById(1);
        if (roomOptional.isPresent()) {
            int index = seats.indexOf(ticket.getSeat());
            Seat seat = seats.get(index);
            seat.setIsTaken(false);
            room.setSeats(seats);
            roomService.updateRoom(room);
            return seat;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!");
    }
}
