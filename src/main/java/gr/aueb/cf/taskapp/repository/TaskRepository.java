package gr.aueb.cf.taskapp.repository;

import gr.aueb.cf.taskapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findById(Long id);
//    List<Task> findByStatus(String status);
    Optional<Task> findByTitle(String title);

}
