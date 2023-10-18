package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.data.OrderRepository;
import from.book.Taco_Cloud_Application.domain.OrderTaco;
import from.book.Taco_Cloud_Application.domain.Taco;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import javax.validation.Valid;

@Controller
@RequestMapping("/current")
@SessionAttributes(names = {"orderTaco"})
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/orders")
    public String getOrder(Model model,OrderTaco orderTaco){
        return "orderTemplate";
    }

    @PostMapping
    public String postOrder(@Valid @ModelAttribute(name = "orderTaco") OrderTaco order, Errors errors, SessionStatus sessionStatus){

        if(errors.hasErrors()){
            return "orderTemplate";
        }

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
