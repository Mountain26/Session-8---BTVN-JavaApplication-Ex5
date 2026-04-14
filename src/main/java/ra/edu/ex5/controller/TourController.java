package ra.edu.ex5.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.edu.ex5.model.dto.TourDto;

@Controller
@RequestMapping("/tour")
public class TourController {

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("tour", new TourDto());
        return "create-tour";
    }

    @PostMapping("/create")
    public String saveTour(@Valid @ModelAttribute("tour") TourDto tour, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create-tour";
        }

        // Trả về trang báo thành công giả lập hoặc redirect tới view thành công.
        model.addAttribute("message", "Tạo tour thành công với mã: " + tour.getTourCode());
        return "create-tour";
    }
}

