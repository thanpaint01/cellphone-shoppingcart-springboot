package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    IProductService productService;

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ModelAndView listProductPage
//            (@RequestParam(value = "page", required = false, defaultValue = "1") int page
//                    , @RequestParam(value = "brand", required = false, defaultValue = "-1") int brand
//                    , @RequestParam(value = "ram", required = false, defaultValue = "-1") int ram
//                    , @RequestParam(value = "rom", required = false, defaultValue = "-1") int rom
//                    , @RequestParam(value = "pin", required = false, defaultValue = "-1") int pin
//                    , @RequestParam(value = "name", required = false, defaultValue = "") String name) {
//        ModelAndView model = new ModelAndView("consumer/product-list");
//        Page<Product> shopPage = productService.findPaginated(page, 15);
//        List<Product> products = shopPage.getContent();
//        model.addObject("currentPage", page);
//        model.addObject("totalPages", shopPage.getTotalPages());
//        model.addObject("totalRecords", shopPage.getTotalElements());
//        model.addObject("products", products);
//        model.addObject("CONTENT_TITLE", "DANH SÁCH SẢN PHẨM");
//        return model;
//    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listProductPageSearch(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page
            , @RequestParam(value = "brand", required = false, defaultValue = "-1") int brandId
            , @RequestParam(value = "ram", required = false, defaultValue = "-1") int ramId
            , @RequestParam(value = "rom", required = false, defaultValue = "-1") int romId
            , @RequestParam(value = "pin", required = false, defaultValue = "-1") int pinId
            , @RequestParam(value = "sort-type", required = false, defaultValue = "-1") int sortType
            , @RequestParam(value = "sort-order", required = false, defaultValue = "-1") int sortOrder
            , @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        int itemPerPage = 15;
        ModelAndView model = new ModelAndView("consumer/product-list");
        Specification<Product> spec = Specification.where(productService.getProductIsActive());
        Pageable pageable = null;
        if (sortOrder > 0 && sortType > 0) {
            Sort sort;
            switch (sortType) {
                case 1:
                    sort = Sort.by("price");
                    break;
                default:
                    sort = Sort.by("id");
                    break;
            }
            switch (sortOrder) {
                case 1:
                    pageable = PageRequest.of(page, itemPerPage, sort.ascending());
                    break;
                case 2:
                    pageable = PageRequest.of(page, itemPerPage, sort.descending());
                    break;
                default:
                    break;
            }
        }
        if (brandId > 0) spec.and(productService.getProductsByBrand(brandId));
        if (ramId > 0) spec.and(productService.getProductsByRam(ramId));
        if (romId > 0) spec.and(productService.getProductsByRom(romId));
        if (pinId > 0) spec.and(productService.getProductsByPin(pinId));
        if (!StringHelper.isNoValue(name)) productService.getProductByName(name);
        if (pageable == null) pageable = PageRequest.of(page, itemPerPage);
        Page<Product> productPage = productService.getPage(spec, pageable);
        model.addObject("currentPage", page);
        model.addObject("totalPages", productPage.getTotalPages());
        model.addObject("totalRecords", productPage.getTotalElements());
        model.addObject("products", productPage.getContent());
        model.addObject("CONTENT_TITLE", "DANH SÁCH SẢN PHẨM");
        return model;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView productDetailPage(@PathVariable(name = "id") int id) {
        Product product;
        if ((product = productService.findOneForConsumer(id)) != null) {
            ModelAndView model = new ModelAndView("consumer/product-detail");
            model.addObject("product", product);
            model.addObject("CONTENT_TITLE", product.getName());
            return model;
        } else {
            return new ModelAndView("redirect:/");
        }

    }
}
