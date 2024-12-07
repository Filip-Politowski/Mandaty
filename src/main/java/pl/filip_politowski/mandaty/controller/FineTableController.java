package pl.filip_politowski.mandaty.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.filip_politowski.mandaty.dto.response.FineResponse;
import pl.filip_politowski.mandaty.model.*;
import pl.filip_politowski.mandaty.service.EmployeeService;
import pl.filip_politowski.mandaty.service.FineService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fines")
public class FineTableController {
    private final FineService fineService;
    private final EmployeeService employeeService;

    @GetMapping
    public String finesTable(Model model, HttpSession session) {
        List<FineResponse> fineResponses = fineService.findAllFinesWithEmployees();
        List<String> companies = employeeService.findAllCompanies();
        model.addAttribute("companies", companies);
        session.isNew();
        model.addAttribute("fines", fineResponses);
        return "fine_table";
    }

    @PostMapping("/view-pdf")
    public ResponseEntity<byte[]> viewPdf(@RequestParam String pdfPath) throws IOException {
        Path path = Paths.get(pdfPath);

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }
        byte[] pdfBytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename(path.getFileName().toString()).build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/delete-pdf")
    public String deletePdfByPath(@RequestParam("pdfPath") String pdfPath) {

        fineService.deletePdfByPath(pdfPath);

        return "redirect:/fines";
    }

    @GetMapping("/search/first-name/last-name")
    public String searchFinesByFirstNameAndLastName(@RequestParam(value = "firstName" , required = false) String firstName,
                                                    @RequestParam(value = "lastName", required = false) String lastName,
                                                    @RequestParam(value = "fineStatus", required = false) FineStatus fineStatus,
                                                    @RequestParam(value = "currency", required = false) Currency currency,
                                                    @RequestParam(value = "violationReason", required = false) ViolationReason violationReason,
                                                    @RequestParam(value = "violationDate", required = false) LocalDate violationDate,
                                                    @RequestParam(value = "paymentDeadline", required = false) LocalDate paymentDeadline,
                                                    @RequestParam(value = "companyName", required = false) String companyName,
                                                    Model model) {
        List<FineResponse> fines = fineService.searchFinesByName(Objects.equals(firstName, "") ? null : firstName, Objects.equals(lastName, "") ? null : lastName, fineStatus,currency , violationReason, violationDate, paymentDeadline, Objects.equals(companyName, "") ? null : companyName);
        List<String> companies = employeeService.findAllCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("nameForm", "name");
        model.addAttribute("companyName", companyName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        if (fineStatus != null) {
            model.addAttribute("fineStatus", fineStatus.toString());
        }

        if (currency != null) {
            model.addAttribute("currency", currency.toString());
        }

        if (violationReason != null) {
            model.addAttribute("violationReason", violationReason.toString());
        }
        model.addAttribute("violationDate", violationDate);
        model.addAttribute("paymentDeadline", paymentDeadline);

        model.addAttribute("fines", fines);
        model.addAttribute("fines", fines);
        return "fine_table";
    }

    @GetMapping("/search/signature")
    public String searchFinesBySignature(@RequestParam(value = "signature") String signature,
                                         @RequestParam(value = "fineStatus", required = false) FineStatus fineStatus,
                                         @RequestParam(value = "currency", required = false) Currency currency,
                                         @RequestParam(value = "violationReason", required = false) ViolationReason violationReason,
                                         @RequestParam(value = "violationDate", required = false) LocalDate violationDate,
                                         @RequestParam(value = "paymentDeadline", required = false) LocalDate paymentDeadline,
                                         @RequestParam(value = "companyName", required = false) String companyName,
                                         Model model) {
        List<FineResponse> fines = fineService.searchFinesBySignature(Objects.equals(signature, "") ? null : signature, fineStatus, currency, violationReason, violationDate, paymentDeadline, Objects.equals(companyName, "") ? null : companyName);

        List<String> companies = employeeService.findAllCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("signatureForm", "signature");
        model.addAttribute("companyName", companyName);
        model.addAttribute("signature", signature);
        if (fineStatus != null) {
            model.addAttribute("fineStatus", fineStatus.toString());
        }

        if (currency != null) {
            model.addAttribute("currency", currency.toString());
        }

        if (violationReason != null) {
            model.addAttribute("violationReason", violationReason.toString());
        }
        model.addAttribute("violationDate", violationDate);
        model.addAttribute("paymentDeadline", paymentDeadline);

        model.addAttribute("fines", fines);
        model.addAttribute("fines", fines);
        return "fine_table";
    }

    @GetMapping("/search")
    public String searchFinesByPhoneNumber(
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "fineStatus", required = false) FineStatus fineStatus,
            @RequestParam(value = "currency", required = false) Currency currency,
            @RequestParam(value = "violationReason", required = false) ViolationReason violationReason,
            @RequestParam(value = "violationDate", required = false) LocalDate violationDate,
            @RequestParam(value = "paymentDeadline", required = false) LocalDate paymentDeadline,
            @RequestParam(value = "companyName", required = false) String companyName,
            Model model) {

        List<FineResponse> fines = fineService.searchFinesAndFilterFines(Objects.equals(phoneNumber, "") ? null : phoneNumber, currency, fineStatus, violationReason, violationDate, paymentDeadline, Objects.equals(companyName, "") ? null : companyName);

        List<String> companies = employeeService.findAllCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("phoneForm", "phone");
        model.addAttribute("companyName", companyName);
        model.addAttribute("phoneNumber", phoneNumber);
        if (fineStatus != null) {
            model.addAttribute("fineStatus", fineStatus.toString());
        }

        if (currency != null) {
            model.addAttribute("currency", currency.toString());
        }

        if (violationReason != null) {
            model.addAttribute("violationReason", violationReason.toString());
        }
        model.addAttribute("violationDate", violationDate);
        model.addAttribute("paymentDeadline", paymentDeadline);

        model.addAttribute("fines", fines);
        return "fine_table";
    }
}
