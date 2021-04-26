package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.User;

public interface IUserService {
    User findOneByEmail(String email);
}
