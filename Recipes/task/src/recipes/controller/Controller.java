package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.services.RecipeService;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@Validated
public class Controller {

    private final RecipeService recipeService;

    @Autowired
    public Controller(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if(recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe wasn't found");
        }
        return recipe.get();
    }

    @GetMapping(value = "recipe/search", params = "category")
    public List<Recipe> findByCategory(@RequestParam @Valid String category) {
        return recipeService.findByCategory(category);
    }

    @GetMapping(value = "recipe/search", params = "name")
    public List<Recipe> findByName(@RequestParam @Valid String name) {
        return recipeService.findByName(name);
    }

    @PostMapping("recipe/new")
    public ResponseEntity<String> postRecipe(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid Recipe recipe) {
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getCategory(), recipe.getDescription(),
                recipe.getIngredients(), recipe.getDirections());
        newRecipe.setEmail(userDetails.getUsername());
        recipeService.saveRecipe(newRecipe);
        String response = "{" +
                "id:" + newRecipe.getId() +
                "}";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }

    @DeleteMapping("recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
        String email = userDetails.getUsername();
        Optional<Recipe> recipe = recipeService.findRecipeById(id);

        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe do not exist.");

        if (!recipe.get().getEmail().equals(email))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can delete only your recipes");

        recipeService.deleteRecipeById(id);
    }

    @PutMapping("recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id, @RequestBody @Valid Recipe recipe) {
        String email = userDetails.getUsername();
        Optional<Recipe> optionalRecipe = recipeService.findRecipeById(id);

        if (optionalRecipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe do not exist.");

        if (!optionalRecipe.get().getEmail().equals(email))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You can update only your recipes");

        recipeService.updateRecipe(id, recipe);
    }
}
