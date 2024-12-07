package pl.filip_politowski.mandaty.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.dto.response.FineResponse;
import pl.filip_politowski.mandaty.mapper.FineMapper;
import pl.filip_politowski.mandaty.model.*;
import pl.filip_politowski.mandaty.repository.EmployeeRepository;
import pl.filip_politowski.mandaty.repository.FineRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineMapper fineMapper;
    private final FineRepository fineRepository;
    private final EmployeeRepository employeeRepository;
    private final PdfService pdfService;

    public boolean isFineSignatureExist(String signature) {
        return fineRepository.existsBySignature(signature);
    }

    public String generateSignature(FineRequest fineRequest) {

        Employee employee = createEmployeeFromRequest(fineRequest);
        return "SIGN-" + employee.getId() + "-" + fineRequest.getViolationDate().toString();
    }

    public Long findFineIdByEmployeeId(Long employeeId) {
        return fineRepository.findFineByEmployeeId(employeeId).getId();
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

    public List<FineResponse> findAllFinesWithEmployees() {
        List<Fine> fines = fineRepository.findAllByOrderByIdDesc();
        fines.forEach(fine -> {
            fine.setEmployee(employeeRepository.findById(fine.getEmployee().getId()).orElse(null));
        });

        return fines.stream().map(fineMapper::toFineResponse).collect(Collectors.toList());
    }

    public void deletePdfByPath(String pdfPath) {
        Optional<Fine> optionalFine = fineRepository.findByPdf(pdfPath);

        if (optionalFine.isPresent()) {
            Fine fine = optionalFine.get();

            pdfService.deleteFile(fine.getPdf());

            fine.setPdf("");
            fineRepository.save(fine);

        }
    }

    public Employee createEmployeeFromRequest(FineRequest fineRequest) {
        Employee employee;
        if (fineRequest.getFirstName().isEmpty() || fineRequest.getLastName().isEmpty()) {
            employee = employeeRepository.findById(fineRequest.getEmployeeId()).orElseThrow();
        } else {
            employee = employeeRepository.findByFirstNameAndLastName(fineRequest.getFirstName(), fineRequest.getLastName());
        }
        return employee;
    }

    public FineResponse findFineByID(Long id) {
        Fine fine = fineRepository.findById(id).orElseThrow();
        fine.setEmployee(employeeRepository.findById(fine.getEmployee().getId()).orElse(null));
        return fineMapper.toFineResponse(fine);
    }

    @Transactional
    public void deleteFineById(Long id) {
        fineRepository.deleteById(id);
    }

    public void changeFineStatus(Long id) {
        Fine fine = fineRepository.findById(id).orElseThrow();
        fine.setFineStatus(FineStatus.PAID);
        fineRepository.save(fine);
    }

    public void updateFine(Long id, FineRequest fineRequest) {
        Fine fine = fineRepository.findById(id).orElseThrow();
        fine.setFineStatus(FineStatus.UNPAID);
        if (fineRequest.getPdf() != null) {
            fine.setPdf(fineRequest.getPdf());
        }
        fine.setEmployee(createEmployeeFromRequest(fineRequest));
        fine.setCurrency(fineRequest.getCurrency());
        fine.setAmount(calculateFinalAmountOfFine(fineRequest));
        Employee employee = createEmployeeFromRequest(fineRequest);
        fine.setEmployee(employee);
        fine.setEmployeeType(fineRequest.getEmployeeType());
        fineRepository.save(fine);
    }

    public List<FineResponse> searchFinesByName(String firstName, String lastName, FineStatus fineStatus, Currency currency, ViolationReason violationReason, LocalDate violationDate, LocalDate paymentDeadline, String companyName) {

        List<Fine> fines = fineRepository.findAllByEmployeeFirstNameAndEmployeeLastName(firstName, lastName, fineStatus, currency, violationReason, violationDate, violationDate, paymentDeadline, paymentDeadline, companyName
        );

        fines.forEach(fine -> {
            fine.setEmployee(employeeRepository.findById(fine.getEmployee().getId()).orElse(null));
        });

        return fines.stream().map(fineMapper::toFineResponse).collect(Collectors.toList());
    }

    public List<FineResponse> searchFinesBySignature(String signature, FineStatus fineStatus, Currency currency, ViolationReason violationReason, LocalDate violationDate, LocalDate paymentDeadline, String companyName) {
        List<Fine> fines = fineRepository.findAllBySignature(signature, fineStatus, currency, violationReason, violationDate, violationDate, paymentDeadline, paymentDeadline, companyName);
        fines.forEach(fine -> {
            fine.setEmployee(employeeRepository.findById(fine.getEmployee().getId()).orElse(null));
        });
        return fines.stream().map(fineMapper::toFineResponse).collect(Collectors.toList());
    }

    public List<FineResponse> searchFinesAndFilterFines(String phoneNumber, Currency currency, FineStatus fineStatus, ViolationReason violationReason, LocalDate violationDate, LocalDate paymentDeadlineDate, String companyName) {
        List<Fine> fines = fineRepository.findAllByEmployeeFilteredEmployee
                (phoneNumber, fineStatus, currency, violationReason, violationDate, violationDate, paymentDeadlineDate, paymentDeadlineDate, companyName);
        fines.forEach(fine -> {
            fine.setEmployee(employeeRepository.findById(fine.getEmployee().getId()).orElse(null));
        });
        return fines.stream().map(fineMapper::toFineResponse).collect(Collectors.toList());
    }
}
