package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service

public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepo;

    @Override
    public boolean isEmailUnique(String email) {
        return userRepo.findOneByEmail(email, User.ACTIVE.ACTIVE.value()) != null;
    }

    @Override
    public User findOneByEmail(String email, int active) {
        return userRepo.findOneByEmail(email, active);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean save(User user) {

        userRepo.save(user);
        return true;

    }

    @Override
    public boolean isTokenUnique(String token) {
        return userRepo.numberOfToken(token) >= 1;
    }

}
