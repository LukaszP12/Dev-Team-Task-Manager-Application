package pl.piwowarski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.piwowarski.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("select u from User u WHERE u.email LIKE %?1%"
            + " OR u.name LIKE %?1%")
    List<User> search(String keyword);

}
