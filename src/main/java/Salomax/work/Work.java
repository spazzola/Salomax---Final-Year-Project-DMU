package Salomax.work;

import Salomax.studio.Studio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "works")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal taxValue;
    private byte hoursDuration;
    private byte minutesDuration;
    private String iconName;

    @ManyToOne
    @JoinColumn(name = "assigned_studio_fk")
    private Studio assignedStudio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return hoursDuration == work.hoursDuration &&
                minutesDuration == work.minutesDuration &&
                Objects.equals(name, work.name) &&
                Objects.equals(price, work.price) &&
                Objects.equals(taxValue, work.taxValue) &&
                Objects.equals(iconName, work.iconName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, taxValue, hoursDuration, minutesDuration, iconName);
    }

}