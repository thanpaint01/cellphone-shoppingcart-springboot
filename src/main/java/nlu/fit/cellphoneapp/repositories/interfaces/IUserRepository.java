package nlu.fit.cellphoneapp.repositories.interfaces;

import nlu.fit.cellphoneapp.entities.User;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email=:email and u.active=:active")
    User findOneByEmail(@Param("email") String email,@Param("active") int active);

}
