package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Rom;
import nlu.fit.cellphoneapp.repositories.interfaces.IRomRepository;
import nlu.fit.cellphoneapp.services.IRomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RomServiceImpl implements IRomService {
    @Autowired
    IRomRepository romRepo;
    @Override
    public List<Rom> findAllByActive(int active) {
        return romRepo.findAllByActive(active);
    }

    @Override
    public Rom findOneById(int id) {
        return romRepo.getOne(id);
    }
}
