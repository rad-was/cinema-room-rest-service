package rad.cinema.room;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rad.cinema.seat.Seat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/seats")
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

    @PostMapping("/purchase")
    public Seat bookSeat(@RequestBody Seat seat) {
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

                return seatToBook;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!");
        }
    }
}
