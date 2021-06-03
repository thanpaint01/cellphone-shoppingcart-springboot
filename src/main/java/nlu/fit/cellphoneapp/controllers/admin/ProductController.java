package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller("adminProductController")
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products-manage")
    public String goToProductManagement(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "find", required = false, defaultValue = "") String find, Model model){
        Page<Product> productPage = productService.findPaginated(page, 10);
        if(find !="" || find != null || !find.equals("")){
            Specification<Product> spec = Specification.where(productService.getProductByName(find));
            Pageable pageable = PageRequest.of(page - 1, 10);
            productPage  = productService.getPage(spec, pageable);
            model.addAttribute("listProduct", productPage.getContent());
            model.addAttribute("find", find);
        }else {
            model.addAttribute("listProduct", productPage.getContent());
        }
        model.addAttribute("sumProductsAmount", productService.findAll().size());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalRecords", productPage.getTotalElements());
        return "admin/admin-product-managerment";
    }

    @PutMapping("/products-manage/{id}")
    public String updateProductInfo(int id) {
        if(null == productService.findOneByID(id)) return "error";

        Product oldProduct = productService.findOneByID(id);

        //update
        return "";
    }
}

