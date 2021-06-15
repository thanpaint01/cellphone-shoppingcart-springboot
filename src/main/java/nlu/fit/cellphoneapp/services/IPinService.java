package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Pin;

import java.util.List;

public interface IPinService {
    List<Pin> findAllByActive(int active);

    Pin findOneById(int id);
    List<Pin> findAll();
}
