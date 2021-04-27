package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface IUserService {
    boolean isEmailUnique(String email);
    User findOneByEmail(String email,int active);
    boolean save(User user);
    boolean isTokenUnique(String token);
}
