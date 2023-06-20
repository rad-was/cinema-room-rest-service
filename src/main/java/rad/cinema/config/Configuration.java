package rad.cinema.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rad.cinema.room.Room;
import rad.cinema.room.RoomRepository;
import rad.cinema.seat.Seat;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository) {
        int rows = 9;
        int columns = 9;

        Room room = new Room();
        room.setTotalRows(rows);
        room.setTotalColumns(columns);
        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <= columns; ++j) {
                seats.add(new Seat(i, j, false));
            }
        }
        room.setAvailableSeats(seats);

        return args -> log.info("Preloading " + roomRepository.save(room));
    }
}
