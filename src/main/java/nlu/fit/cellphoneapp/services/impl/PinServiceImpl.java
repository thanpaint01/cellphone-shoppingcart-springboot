package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Pin;
import nlu.fit.cellphoneapp.repositories.interfaces.IPinRepository;
import nlu.fit.cellphoneapp.services.IPinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PinServiceImpl implements IPinService {
    @Autowired
    IPinRepository pinRepo;
    @Override
    public List<Pin> findAllByActive(int active) {
        return pinRepo.findAllByActive(active);
    }

    @Override
    public Pin findOneById(int id) {
        return pinRepo.getOne(id);
    }
}
