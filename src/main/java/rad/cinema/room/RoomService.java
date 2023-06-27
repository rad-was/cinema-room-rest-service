package rad.cinema.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rad.cinema.seat.Seat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Optional<Room> findRoomById(Integer id) {
        return roomRepository.findRoomById(id);
    }

    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    public List<Seat> findAvailableSeats(Room room) {
        return room.getSeats().stream()
                .filter(seat -> !seat.getIsTaken())
                .collect(Collectors.toList());
    }

    public String getAvailableSeats(Integer roomId) throws JsonProcessingException {
        Optional<Room> roomOptional = findRoomById(roomId);

        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            Map<String, Object> json = new LinkedHashMap<>();
            json.put("total_rows", room.getTotalRows());
            json.put("total_columns", room.getTotalColumns());
            json.put("available_seats", findAvailableSeats(room));

            return new ObjectMapper().writeValueAsString(json);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!");
    }

}
