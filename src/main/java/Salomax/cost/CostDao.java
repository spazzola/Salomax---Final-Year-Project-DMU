package Salomax.cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostDao extends JpaRepository<Cost, Long> {

}