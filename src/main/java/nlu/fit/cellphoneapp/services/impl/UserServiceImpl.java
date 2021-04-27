package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepo;

    @Override
    public boolean isEmailUnique(String email) {
        return userRepo.findOneByEmail(email,User.ACTIVE.UNVERTIFIED.value()) !=null;
    }
}
