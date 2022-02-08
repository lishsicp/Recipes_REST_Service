package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.dao.RecipeRepository;
import recipes.entities.Recipe;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findRecipeById(Integer id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(Integer id) {
        recipeRepository.deleteById(id);
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

}
