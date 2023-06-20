package rad.cinema.room;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Optional<Room> findRoomById(Integer id) {
        return roomRepository.findById(id);
    }
}
