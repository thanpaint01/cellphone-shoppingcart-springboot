package nlu.fit.cellphoneapp.services;
import nlu.fit.cellphoneapp.entities.Ram;
import java.util.List;

public interface IRamService {
    List<Ram> findAllByActive(int active);
}