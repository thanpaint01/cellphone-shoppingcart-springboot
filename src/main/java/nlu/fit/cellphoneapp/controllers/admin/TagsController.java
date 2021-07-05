package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.DTOs.BrandDTO;
import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.entities.Pin;
import nlu.fit.cellphoneapp.entities.Ram;
import nlu.fit.cellphoneapp.entities.Rom;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.IPinService;
import nlu.fit.cellphoneapp.services.IRamService;
import nlu.fit.cellphoneapp.services.IRomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    //edit and delete
    @DeleteMapping("brands/{id}")
    @ResponseBody
    public String deleteATag(@PathVariable int id){
        if(null == brandService.findOneById(id)) return "fail";
        Brand brand = (Brand) brandService.findOneById(id);
        if(brand.getActive() == 1){
            brand.setActive(-1);
        }else{
            brand.setActive(1);
        }
        brandService.save(brand);
        return "success";
    }
    @DeleteMapping("rams/{id}")
    @ResponseBody
    public String deleteATagRam(@PathVariable int id){
        if(null == ramService.findOneById(id)) return "fail";
        Ram ram = (Ram) ramService.findOneById(id);
        if(ram.getActive() == 1){
            ram.setActive(-1);
        }else{
            ram.setActive(1);
        }
        ramService.save(ram);
        return "success";
    }
    @DeleteMapping("roms/{id}")
    @ResponseBody
    public String deleteATagRom(@PathVariable int id){
        if(null == romService.findOneById(id)) return "fail";
        Rom rom = (Rom) romService.findOneById(id);
        if(rom.getActive() == 1){
            rom.setActive(-1);
        }else{
            rom.setActive(1);
        }
        romService.save(rom);
        return "success";
    }
    @DeleteMapping("pins/{id}")
    @ResponseBody
    public String deleteATagPin(@PathVariable int id){
        if(null == romService.findOneById(id)) return "fail";
        Pin pin = (Pin) pinService.findOneById(id);
        if(pin.getActive() == 1){
            pin.setActive(-1);
        }else{
            pin.setActive(1);
        }
        pinService.save(pin);
        return "success";
    }

    //add new tags
    @PostMapping("brands/new")
    @ResponseBody
    public String createdNewBrand(@RequestBody BrandDTO brandDTO){
        return "";
    }


}