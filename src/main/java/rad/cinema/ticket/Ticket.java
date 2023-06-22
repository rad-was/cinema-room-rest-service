package rad.cinema.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rad.cinema.seat.Seat;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {

    @Id
    private UUID token;

    @OneToOne
    @JoinColumn(name="seat_id", referencedColumnName="id")
    private Seat seat;

    @JsonIgnore
    public Seat getSeat() {
        return seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(token, ticket.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
