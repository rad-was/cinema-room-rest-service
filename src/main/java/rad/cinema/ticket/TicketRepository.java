package rad.cinema.ticket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

}
