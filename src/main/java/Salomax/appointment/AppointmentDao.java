package Salomax.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<List<Appointment>> findByEndDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}