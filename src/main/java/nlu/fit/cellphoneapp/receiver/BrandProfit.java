package nlu.fit.cellphoneapp.receiver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public interface BrandProfit {
    Integer getId();

    String getBrandname();

    String getImg();

    Integer getActive();

    Double getProfit();
}
