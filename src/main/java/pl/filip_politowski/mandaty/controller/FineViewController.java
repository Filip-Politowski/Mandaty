package pl.filip_politowski.mandaty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.filip_politowski.mandaty.dto.response.FineResponse;
import pl.filip_politowski.mandaty.service.FineService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fine-view")
public class FineViewController {

    private final FineService fineService;


    @GetMapping("/{id}")
    public String showFineView(@PathVariable Long id, Model model) {
        FineResponse fineResponse = fineService.findFineByID(id);
        model.addAttribute("fine", fineResponse);
        return "fine_view";
    }

    @PostMapping("/delete-fine/{id}")
    public String deleteFine(@PathVariable Long id) {
        fineService.deleteFineById(id);
        return "redirect:/fines";
    }

    @PostMapping("/pay-fine/{id}")
    public String payFine(@PathVariable Long id) {
        fineService.changeFineStatus(id);
        return "redirect:/fine-view/" + id;
    }
}
