package rad.cinema.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Embeddable
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_row", "seat_column"})
})
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "seat_row", nullable = false)
    private Integer row;

    @NonNull
    @Column(name = "seat_column", nullable = false)
    private Integer column;

    @NonNull
    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Boolean getTaken() {
        return isTaken;
    }
}
