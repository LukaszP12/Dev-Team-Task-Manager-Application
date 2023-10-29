package pl.piwowarski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piwowarski.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t WHERE t.name LIKE %?1%"
            + " OR t.description LIKE %?1%"
            + " OR t.creatorName LIKE %?1%")
    List<Task> search(String keyword);

}
