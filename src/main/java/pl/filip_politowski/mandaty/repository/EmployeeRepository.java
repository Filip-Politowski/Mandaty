package pl.filip_politowski.mandaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.model.EmployeeType;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstNameAndLastName(String firstName, String lastName);
    List<Employee> findAllByEmployeeType(EmployeeType employeeType);
    @Query("SELECT DISTINCT e.companyName FROM Employee e")
    List<String> findAllUniqueCompanies();
}
