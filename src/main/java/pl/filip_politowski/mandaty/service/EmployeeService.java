package pl.filip_politowski.mandaty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
