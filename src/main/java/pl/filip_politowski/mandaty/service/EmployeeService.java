package pl.filip_politowski.mandaty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.model.EmployeeType;
import pl.filip_politowski.mandaty.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> findAllPhysicalEmployees() {
        return employeeRepository.findAllByEmployeeType(EmployeeType.PHYSICAL);
    }

    public List<String> findAllCompanies() {
        return employeeRepository.findAllUniqueCompanies();
    }

    public Employee findEmployeeByName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
