package rad.cinema.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rad.cinema.seat.Seat;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Integer id;

    @NonNull
    @Column(name = "total_rows")
    private Integer totalRows;

    @NonNull
    @Column(name = "total_columns")
    private Integer totalColumns;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @JsonIgnore
    public Integer getId() {
        return id;
    }
}
