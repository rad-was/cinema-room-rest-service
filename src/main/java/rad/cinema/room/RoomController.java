package rad.cinema.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/seats")
    public String getSeats() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        Optional<Room> room = roomService.findRoomById(1);

        if (room.isPresent()) {
            Room roomObject = room.get();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(roomObject);
        } else {
            return "Room not found";
        }
    }
}
