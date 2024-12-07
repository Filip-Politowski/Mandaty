package pl.filip_politowski.mandaty.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.service.EmailService;
import pl.filip_politowski.mandaty.service.EmployeeService;
import pl.filip_politowski.mandaty.service.PdfService;
import pl.filip_politowski.mandaty.service.FineService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/add_fine")
public class AddFineController {
    private final FineService fineService;
    private final EmployeeService employeeService;
    private final PdfService pdfService;
    private final EmailService emailService;


    @GetMapping
    private String addingFinePage(HttpSession session) {

        List<Employee> employees = employeeService.findAllPhysicalEmployees();
        session.setAttribute("employees", employees);
        return "form_add_fine";
    }


    @PostMapping
    public String addFine(FineRequest fineRequest,
                          @RequestParam("file") MultipartFile file,
                          Model model, RedirectAttributes redirectAttributes) {
        try {

            Employee employee = fineService.createEmployeeFromRequest(fineRequest);

            if (employee == null) {
                model.addAttribute("errorMessage", "Employee with name " + fineRequest.getFirstName() + " " + fineRequest.getLastName() + " does not exist in the system.");
                return "form_add_fine";
            }

            String signature = fineService.generateSignature(fineRequest);

            if (fineService.isFineSignatureExist(signature)) {
                model.addAttribute("errorMessage", "Fine with this signature already exists in the system.");
                return "form_add_fine";
            }

            String filePath = pdfService.uploadFile(file);
            fineRequest.setPdf(filePath);

            fineService.saveFineInRepository(fineRequest);

            emailService.sendEmail("j.kowalski@test.pl", "Fine nr. " + signature + " request to pay", fineRequest, signature);
            redirectAttributes.addFlashAttribute("successMessage", "Fine has been successfully added.");
            return "redirect:/fines";

        } catch (IOException e) {

            model.addAttribute("errorMessage", "File cannot be uploaded: " + e.getMessage());
            return "form_add_fine";
        }
    }

}
