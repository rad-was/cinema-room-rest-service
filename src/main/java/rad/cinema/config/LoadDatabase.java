package rad.cinema.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rad.cinema.room.Room;
import rad.cinema.room.RoomRepository;
import rad.cinema.seat.Seat;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    public CommandLineRunner initDatabase(RoomRepository roomRepository) {
        int rows = 9;
        int columns = 9;

        Room room = new Room();
        room.setTotalRows(rows);
        room.setTotalColumns(columns);
        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <= columns; ++j) {

                int price = i <= 4 ? 10 : 8;
                Seat seat = new Seat(i, j);
                seat.setPrice(price);

                seats.add(seat);
            }
        }
        room.setSeats(seats);

        return args -> log.info("Preloading " + roomRepository.save(room));
    }
}
