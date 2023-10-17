package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.domain.OrderTaco;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/current")
public class OrderController {

    @GetMapping("/orders")
    public String getOrder(Model model, OrderTaco order){
        model.addAttribute("order",order);
        return "orderTemplate";
    }

    @PostMapping
    public String postOrder(){
        return "redirect:/";
    }
}
