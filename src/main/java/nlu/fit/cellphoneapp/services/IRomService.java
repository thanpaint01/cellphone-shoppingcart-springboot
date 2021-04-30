package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Rom;

import java.util.List;

public interface IRomService {
    List<Rom> findAllByActive(int active);
}
