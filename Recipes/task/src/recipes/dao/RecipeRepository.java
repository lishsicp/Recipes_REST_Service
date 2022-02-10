package recipes.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import recipes.entities.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    void deleteById(Long id);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

}