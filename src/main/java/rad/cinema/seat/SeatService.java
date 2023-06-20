package rad.cinema.seat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

}
