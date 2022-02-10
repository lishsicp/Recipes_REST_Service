package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.dao.RecipeRepository;
import recipes.entities.Recipe;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void updateRecipe(long id, Recipe newRecipe) {
        Optional<Recipe> recipe = findRecipeById(id);
        if (recipe.isPresent()) {
            recipe.get().setName(newRecipe.getName());
            recipe.get().setCategory(newRecipe.getCategory());
            recipe.get().setDescription(newRecipe.getDescription());
            recipe.get().setDate(LocalDateTime.now());
            recipe.get().setIngredients(newRecipe.getIngredients());
            recipe.get().setDirections(newRecipe.getDirections());
            saveRecipe(recipe.get());
        }
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

}
