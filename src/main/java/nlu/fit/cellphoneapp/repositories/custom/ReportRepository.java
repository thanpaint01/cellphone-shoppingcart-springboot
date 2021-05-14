package nlu.fit.cellphoneapp.repositories.custom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@AllArgsConstructor
public class ReportRepository {
    @PersistenceContext
    EntityManager entityManager;

}
