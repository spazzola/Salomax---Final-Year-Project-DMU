package Salomax.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository <Employee, Long> {

    List<Employee> findByAssignedStudioId(Long id);

}