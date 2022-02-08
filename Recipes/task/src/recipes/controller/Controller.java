package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.services.RecipeService;

import javax.validation.Valid;
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
    public Recipe getRecipe(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if(recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe wasn't found");
        }
        return recipe.get();
    }

    @PostMapping("recipe/new")
    public ResponseEntity<String> postRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getDescription(),
                recipe.getIngredients(), recipe.getDirections());
        recipeService.saveRecipe(newRecipe);
        String response = "{" +
                "id:" + newRecipe.getId() +
                "}";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe do not exist.");
        } else {
            recipeService.deleteRecipeById(id);
        }
    }

}
