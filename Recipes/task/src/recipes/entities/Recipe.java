package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotBlank
    private String category;

    @JsonProperty
    @NotBlank
    private String description;

    @JsonProperty
    @NonNull
    private LocalDateTime date;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    public Recipe(String name, String category, String description, List<String> ingredients, @NonNull List<String> directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.date = LocalDateTime.now();
        this.ingredients = ingredients;
        this.directions = directions;
    }

}