package recipes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}