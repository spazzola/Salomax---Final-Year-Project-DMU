package Salomax.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository <Address, Long> {

}