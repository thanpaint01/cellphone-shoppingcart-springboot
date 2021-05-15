package nlu.fit.cellphoneapp.repositories.custom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@AllArgsConstructor
public class ReportRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public int test() {
        String sql = "SELECT COUNT(*) FROM product";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = this.em.createNativeQuery("SELECT a.id, a.firstName, a.lastName, a.version FROM Author a").getResultList();

        results.stream().forEach((record) -> {
            Long id = ((BigInteger) record[0]).longValue();
            String firstName = (String) record[1];
            String lastName = (String) record[2];
            Integer version = (Integer) record[3];
        });
        return ((Number) query.getSingleResult()).intValue();
    }
}
