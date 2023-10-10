package from.book.Taco_Cloud_Application.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "OrderedTaco")
@Entity
public class OrderTaco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "deliveryName cannot be blank")
    private String deliveryName;
    @NotBlank(message = "deliveryStreet cannot be blank")
    private String deliveryStreet;
    @NotBlank(message = "deliveryCity cannot be blank")
    private String deliveryCity;
    @NotBlank(message = "deliveryCity cannot be blank")
    private String deliveryState;
    @NotBlank(message = "deliveryZip cannot be blank")
    private String deliveryZip;

    @CreditCardNumber(message = "ccNumber cannot be blank")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;
    @NotBlank(message = "ccCVV cannot be blank")
    private String ccCVV;

    private Date placeAt;

    @PrePersist
    public void prepareDate(){
        this.placeAt = new Date();
    }

    private List<Taco> tacos = new ArrayList<>();

    public void addTacoToOrder(Taco taco){
        this.tacos.add(taco);
    }
}
