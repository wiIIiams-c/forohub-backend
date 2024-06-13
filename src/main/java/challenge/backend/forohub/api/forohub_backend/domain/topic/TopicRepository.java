package challenge.backend.forohub.api.forohub_backend.domain.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long>{

        @Query("""
                SELECT t.title
                FROM Topic t
                WHERE REPLACE(t.title, " ", "") = :title
                """)
        String findByTitle(String title);

        @Query("""
                SELECT t.message
                FROM Topic t
                WHERE REPLACE(t.message, " ", "") = :message
                """)
        String findByMessage(String message);

}
