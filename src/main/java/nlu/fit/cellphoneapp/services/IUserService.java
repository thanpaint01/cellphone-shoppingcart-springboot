package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.User;


public interface IUserService {
    boolean isEmailUnique(String email);

    User findOneByEmail(String email, int active);

    boolean save(User user);

    boolean isTokenUnique(String token);

    User findOneByLogin(String email, String password);

    User verifyEmail(String token);


}
