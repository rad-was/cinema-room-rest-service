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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

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

                UUID uuid = UUID.randomUUID();
                Map<String, Object> json = new LinkedHashMap<>();
                json.put("token", uuid);
                json.put("ticket", seatToBook);

                return new ObjectMapper().writeValueAsString(json);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!");
        }
    }
}
