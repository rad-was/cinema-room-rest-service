package rad.cinema.seat;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SeatController {

    private final SeatService seatService;

}
