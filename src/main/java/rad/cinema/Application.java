package rad.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rad.cinema.seat.SeatRepository;

@SpringBootApplication
public class Application {

    private SeatRepository seatRepository;

    public Application(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
