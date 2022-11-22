package Salomax.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDao extends JpaRepository<Work, Long> {

}
