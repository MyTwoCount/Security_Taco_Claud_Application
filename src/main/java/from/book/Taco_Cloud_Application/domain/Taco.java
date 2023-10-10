package from.book.Taco_Cloud_Application.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Min(value = 5,message = "Name for your taco must have at least five characters")
    private String tacoName;

    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredientList = new ArrayList<>();

    private Date createdAt;

    @PrePersist
    public void prepareCreatedData(){
        this.createdAt = new Date();
    }
}