package Salomax.cost;

import Salomax.studio.Studio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "costs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cost_id")
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private double taxValue;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime addedDate;

    @ManyToOne
    @JoinColumn(name = "assigned_studio_fk")
    private Studio assignedStudio;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return quantity == cost.quantity &&
                Double.compare(cost.taxValue, taxValue) == 0 &&
                Objects.equals(name, cost.name) &&
                Objects.equals(price, cost.price) &&
                Objects.equals(addedDate, cost.addedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, taxValue, addedDate);
    }
}