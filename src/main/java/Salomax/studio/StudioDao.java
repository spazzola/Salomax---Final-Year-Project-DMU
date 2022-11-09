package Salomax.studio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioDao extends JpaRepository <Studio, Long> {

}