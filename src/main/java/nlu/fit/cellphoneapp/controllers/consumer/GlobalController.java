package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.entities.Pin;
import nlu.fit.cellphoneapp.entities.Ram;
import nlu.fit.cellphoneapp.entities.Rom;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.IPinService;
import nlu.fit.cellphoneapp.services.IRamService;
import nlu.fit.cellphoneapp.services.IRomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(basePackages = "nlu.fit.cellphoneapp.controllers")
public class GlobalController {
    @Autowired
    IBrandService brandService;
    @Autowired
    IPinService pinService;
    @Autowired
    IRomService romService;
    @Autowired
    IRamService ramService;

    @ModelAttribute("allBrands")
    public List<Brand> allBrands() {
        return brandService.findAllByActive(1);
    }
    @ModelAttribute("allRams")
    public List<Ram> allRams() {
        return ramService.findAllByActive(1);
    }

    @ModelAttribute("allRoms")
    public List<Rom> allRoms() {
        return romService.findAllByActive(1);
    }

    @ModelAttribute("allPins")
    public List<Pin> allPins() {
        return pinService.findAllByActive(1);
    }


}
