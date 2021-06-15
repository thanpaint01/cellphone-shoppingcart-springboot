package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.IPinService;
import nlu.fit.cellphoneapp.services.IRamService;
import nlu.fit.cellphoneapp.services.IRomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("adminTagsManagement")
@RequestMapping("/admin/tags-manage")
public class TagsController {

    @Autowired
    IBrandService brandService;
    @Autowired
    IRamService ramService;
    @Autowired
    IRomService romService;
    @Autowired
    IPinService pinService;

    @GetMapping("brands")
    public ModelAndView brandsPageManagement(){
        ModelAndView mv = new ModelAndView("admin/admin-brand-tag-management");
        mv.addObject("brands", brandService.findAll());
        return mv;
    }

    @GetMapping("rams")
    public ModelAndView ramsPageManagement(){
        ModelAndView mv = new ModelAndView("admin/admin-ram-tag-management");
        mv.addObject("rams", ramService.findAll());
        return mv;
    }

    @GetMapping("roms")
    public ModelAndView romsPageManagement(){
        ModelAndView mv = new ModelAndView("admin/admin-rom-tag-management");
        mv.addObject("roms", romService.findAll());
        return mv;
    }

    @GetMapping("pins")
    public ModelAndView pinsPageManagement(){
        ModelAndView mv = new ModelAndView("admin/admin-pin-tag-management");
        mv.addObject("pins", pinService.findAll());
        return mv;
    }

}
