package pl.filip_politowski.mandaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.filip_politowski.mandaty.model.Currency;
import pl.filip_politowski.mandaty.model.Fine;
import pl.filip_politowski.mandaty.model.FineStatus;
import pl.filip_politowski.mandaty.model.ViolationReason;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsBySignature(String signature);
    List<Fine> findAllByOrderByIdDesc();
    Optional<Fine> findByPdf(String pdfPath);
    Fine findFineByEmployeeId(long employeeId);


    @Query("SELECT f FROM Fine f " +
            "WHERE (:firstName IS NULL OR f.employee.firstName = :firstName) " +
            "AND (:lastName IS NULL OR f.employee.lastName = :lastName) " +
            "AND (:status IS NULL OR f.fineStatus = :status) " +
            "AND (:currency IS NULL OR f.currency = :currency) " +
            "AND (:reason IS NULL OR f.violationReason = :reason) " +
            "AND (:violationDateFrom IS NULL OR f.violationDate >= :violationDateFrom) " +
            "AND (:violationDateTo IS NULL OR f.violationDate <= :violationDateTo) " +
            "AND (:paymentDeadlineFrom IS NULL OR f.paymentDeadline >= :paymentDeadlineFrom) " +
            "AND (:paymentDeadlineTo IS NULL OR f.paymentDeadline <= :paymentDeadlineTo) " +
            "AND (:company IS NULL OR f.employee.companyName = :company)")
    List<Fine> findAllByEmployeeFirstNameAndEmployeeLastName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("status") FineStatus status,
            @Param("currency") Currency currency,
            @Param("reason") ViolationReason violationReason,
            @Param("violationDateFrom") LocalDate violationDateFrom,
            @Param("violationDateTo") LocalDate violationDateTo,
            @Param("paymentDeadlineFrom") LocalDate paymentDeadlineFrom,
            @Param("paymentDeadlineTo") LocalDate paymentDeadlineTo,
            @Param("company") String company
    );

    @Query("SELECT f FROM Fine f " +
            "WHERE (:signature IS NULL OR f.signature = :signature) " +
            "AND (:status IS NULL OR f.fineStatus = :status) " +
            "AND (:currency IS NULL OR f.currency = :currency) " +
            "AND (:reason IS NULL OR f.violationReason = :reason) " +
            "AND (:violationDateFrom IS NULL OR f.violationDate >= :violationDateFrom) " +
            "AND (:violationDateTo IS NULL OR f.violationDate <= :violationDateTo) " +
            "AND (:paymentDeadlineFrom IS NULL OR f.paymentDeadline >= :paymentDeadlineFrom) " +
            "AND (:paymentDeadlineTo IS NULL OR f.paymentDeadline <= :paymentDeadlineTo) " +
            "AND (:company IS NULL OR f.employee.companyName = :company)")
    List<Fine> findAllBySignature(
            @Param("signature") String signature,
            @Param("status") FineStatus status,
            @Param("currency") Currency currency,
            @Param("reason") ViolationReason violationReason,
            @Param("violationDateFrom") LocalDate violationDateFrom,
            @Param("violationDateTo") LocalDate violationDateTo,
            @Param("paymentDeadlineFrom") LocalDate paymentDeadlineFrom,
            @Param("paymentDeadlineTo") LocalDate paymentDeadlineTo,
            @Param("company") String company
    );

    @Query("SELECT f FROM Fine f " +
            "WHERE (:phoneNumber IS NULL OR f.employee.phoneNumber = :phoneNumber) " +
            "AND (:status IS NULL OR f.fineStatus = :status) " +
            "AND (:currency IS NULL OR f.currency = :currency) " +
            "AND (:reason IS NULL OR f.violationReason = :reason) " +
            "AND (:violationDateFrom IS NULL OR f.violationDate >= :violationDateFrom) " +
            "AND (:violationDateTo IS NULL OR f.violationDate <= :violationDateTo) " +
            "AND (:paymentDeadlineFrom IS NULL OR f.paymentDeadline >= :paymentDeadlineFrom) " +
            "AND (:paymentDeadlineTo IS NULL OR f.paymentDeadline <= :paymentDeadlineTo) " +
            "AND (:company IS NULL OR f.employee.companyName = :company)")
    List<Fine> findAllByEmployeeFilteredEmployee(
            @Param("phoneNumber") String phoneNumber,
            @Param("status") FineStatus status,
            @Param("currency") Currency currency,
            @Param("reason") ViolationReason violationReason,
            @Param("violationDateFrom") LocalDate violationDateFrom,
            @Param("violationDateTo") LocalDate violationDateTo,
            @Param("paymentDeadlineFrom") LocalDate paymentDeadlineFrom,
            @Param("paymentDeadlineTo") LocalDate paymentDeadlineTo,
            @Param("company") String company
    );
}
