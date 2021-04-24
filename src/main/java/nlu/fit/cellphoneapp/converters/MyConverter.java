package nlu.fit.cellphoneapp.converters;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.Cart;

public class MyConverter {

    public static Cart toEntity(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setActive(1);
        cart.setAmount(cartDTO.getAmount());
        return cart;
    }
}
