package rad.cinema.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Embeddable
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_row", "seat_column"})
})
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Seat {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "seat_row", nullable = false)
    private Integer row;

    @NonNull
    @Column(name = "seat_column", nullable = false)
    private Integer column;

    private Integer price;

    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken = false;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Boolean getIsTaken() {
        return isTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(row, seat.row) && Objects.equals(column, seat.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
