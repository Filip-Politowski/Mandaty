package pl.filip_politowski.mandaty.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.model.Employee;
import pl.filip_politowski.mandaty.service.EmployeeService;
import pl.filip_politowski.mandaty.service.FileUploadService;
import pl.filip_politowski.mandaty.service.FineService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/add_fine")
public class AddFineController {
    private final FineService fineService;
    private final EmployeeService employeeService;
    private final FileUploadService fileUploadService;


    @GetMapping
    private String addingFinePage(HttpSession session) {

        List<Employee> employees = employeeService.getAllEmployees();
        session.setAttribute("employees", employees);
        return "form_add_fine";
    }


    @PostMapping
    public String addFine(FineRequest fineRequest,
                          @RequestParam("file") MultipartFile file,
                          Model model, HttpSession session) {
        try {

            String signature = fineService.generateSignature(fineRequest);
            if (fineService.isFineExist(signature)) {
                model.addAttribute("errorMessage", "Fine with this signature already exists in the system.");
                return "form_add_fine";
            }

            String filePath = fileUploadService.uploadFile(file);
            fineRequest.setPdf(filePath);

            fineService.saveFineInRepository(fineRequest);

            session.setAttribute("positiveMessage", "Fine added successfully.");
            return "redirect:/add_fine";

        } catch (IOException e) {
            model.addAttribute("errorMessage", "File cannot be uploaded: " + e.getMessage());
            return "form_add_fine";
        }
    }

}
