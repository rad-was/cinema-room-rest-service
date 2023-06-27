package rad.cinema.room;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    Optional<Room> findRoomById(Integer id);
}
