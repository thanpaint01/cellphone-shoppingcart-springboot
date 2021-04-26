package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepo;

    @Override
    public User findOneByEmail(String email) {
        return userRepo.findOneByEmail(email);
    }
}
