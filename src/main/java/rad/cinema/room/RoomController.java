package rad.cinema.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping(value = "/seats", produces = "application/json")
    public String getAvailableSeats() throws JsonProcessingException {
        return roomService.getAvailableSeats(1);
    }
}
