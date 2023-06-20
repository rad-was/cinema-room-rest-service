package rad.cinema.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Embeddable
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_row", "seat_column"})
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_row", nullable = false)
    private Integer row;

    @Column(name = "seat_column", nullable = false)
    private Integer column;

    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken;

    public Seat() {
    }

    public Seat(Integer row, Integer column, Boolean isTaken) {
        this.row = row;
        this.column = column;
        this.isTaken = isTaken;
    }

    public Seat(Long id, Integer row, Integer column, Boolean isTaken) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.isTaken = isTaken;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    @JsonIgnore
    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(row, seat.row) && Objects.equals(column, seat.column) && Objects.equals(isTaken, seat.isTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, column, isTaken);
    }
}
