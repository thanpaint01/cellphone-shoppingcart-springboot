package nlu.fit.cellphoneapp.repositories.interfaces;

import nlu.fit.cellphoneapp.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Integer> {
}