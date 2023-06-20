package rad.cinema.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Iterable<Seat> findAllSeats() {
        return seatRepository.findAll();
    }

    public Seat save(Seat toSave) {
        return seatRepository.save(toSave);
    }
}
