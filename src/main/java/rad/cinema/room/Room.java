package rad.cinema.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import rad.cinema.seat.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "total_rows")
    private Integer totalRows;

    @Column(name = "total_columns")
    private Integer totalColumns;

    @Column(name = "available_seats")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> availableSeats = new ArrayList<>();

    public Room(Integer id, Integer totalRows, Integer totalColumns, List<Seat> availableSeats) {
        this.id = id;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = availableSeats;
    }

    public Room() {
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(Integer totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(totalRows, room.totalRows) && Objects.equals(totalColumns, room.totalColumns) && Objects.equals(availableSeats, room.availableSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalRows, totalColumns, availableSeats);
    }
}
