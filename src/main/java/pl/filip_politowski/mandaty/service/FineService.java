package pl.filip_politowski.mandaty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.mapper.FineMapper;
import pl.filip_politowski.mandaty.model.Currency;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.model.Fine;
import pl.filip_politowski.mandaty.model.FineStatus;
import pl.filip_politowski.mandaty.repository.EmployeeRepository;
import pl.filip_politowski.mandaty.repository.FineRepository;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineMapper fineMapper;
    private final FineRepository fineRepository;
    private final EmployeeRepository employeeRepository;

    public boolean isFineExist(String signature) {
        return fineRepository.existsBySignature(signature);
    }

    public String generateSignature(FineRequest fineRequest) {

        Employee employee = createEmployeeFromRequest(fineRequest);
        return "SIGN-" + employee.getId() + "-" + fineRequest.getViolationDate().toString();
    }

    public Double calculateFinalAmountOfFine(FineRequest fineRequest) {
        double administrativeFee = 100.00;
        if (fineRequest.getCurrency().equals(Currency.EUR)) {
            return Math.round((fineRequest.getAmount() + administrativeFee / 4.2) * 100.0) / 100.0;
        } else {
            return fineRequest.getAmount() + administrativeFee;
        }
    }

    public void saveFineInRepository(FineRequest fineRequest) {

        String generatedSignature = generateSignature(fineRequest);
        Fine fine = fineMapper.toFine(fineRequest);
        fine.setFineStatus(FineStatus.UNPAID);
        fine.setAdministrativeFee(100.00);
        fine.setAmount(calculateFinalAmountOfFine(fineRequest));
        Employee employee = createEmployeeFromRequest(fineRequest);
        fine.setEmployee(employee);
        fine.setSignature(generatedSignature);
        fineRepository.save(fine);
    }

    private Employee createEmployeeFromRequest(FineRequest fineRequest) {
        Employee employee;
        if (fineRequest.getFirstName().isEmpty() || fineRequest.getLastName().isEmpty()) {
            employee = employeeRepository.findById(fineRequest.getEmployeeId()).orElseThrow();
        } else {
            employee = employeeRepository.findByFirstNameAndLastName(fineRequest.getFirstName(), fineRequest.getLastName());
        }
        return employee;
    }
}
