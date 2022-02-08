package recipes.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import recipes.entities.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    void deleteById(Integer id);
}