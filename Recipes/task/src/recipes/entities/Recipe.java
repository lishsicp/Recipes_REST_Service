package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotBlank
    private String description;


    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    public Recipe(String name, String description, @NonNull List<String> ingredients, @NonNull List<String> directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

}