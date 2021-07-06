package nlu.fit.cellphoneapp.DTOs;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import nlu.fit.cellphoneapp.entities.Brand;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Validated
public class BrandDTO {
    private int id;
    @NotBlank(message = "Không thể bỏ trống trường này!")
    private String name;
    private MultipartFile logo;
    private int active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "BrandDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo=" + logo +
                ", active=" + active +
                '}';
    }

    public Brand toBrandEntity() throws IOException {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setActive(active);
        brand.setId(id);
        return brand;
    }
}
