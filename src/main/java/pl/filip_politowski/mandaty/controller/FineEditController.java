package pl.filip_politowski.mandaty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.dto.response.FineResponse;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.service.EmployeeService;
import pl.filip_politowski.mandaty.service.FineService;
import pl.filip_politowski.mandaty.service.PdfService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/edit-fine")
public class FineEditController {

    private final FineService fineService;
    private final EmployeeService employeeService;
    private final PdfService pdfService;

    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FineResponse fineResponse = fineService.findFineByID(id);
        List<Employee> employees = employeeService.findAllPhysicalEmployees();
        model.addAttribute("fine", fineResponse);
        model.addAttribute("employees", employees);
        return "edit_fine";
    }

    @PostMapping("/{id}")
    public String updateFine(@PathVariable Long id,
                             @RequestParam("file") MultipartFile file,
                             FineRequest fineRequest,
                             Model model) {
        try {
            Employee employee = employeeService.findEmployeeByName(fineRequest.getFirstName(), fineRequest.getLastName());


            if (employee == null) {
                model.addAttribute("errorMessage", "Employee with name " + fineRequest.getFirstName() + " " + fineRequest.getLastName() + " does not exist in the system.");

                FineResponse fineResponse = fineService.findFineByID(id);
                List<Employee> employees = employeeService.findAllPhysicalEmployees();
                model.addAttribute("fine", fineResponse);
                model.addAttribute("employees", employees);
                return "edit_fine";
            }


            if (!file.isEmpty()) {
                String filePath = pdfService.uploadFile(file);
                fineRequest.setPdf(filePath);
            }


            fineService.updateFine(id, fineRequest);
            return "redirect:/fine-view/" + id;
        } catch (IOException e) {

            model.addAttribute("errorMessage", "File cannot be uploaded: " + e.getMessage());
            return "redirect:/fine-view/" + id;
        }
    }
}
