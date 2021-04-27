package nlu.fit.cellphoneapp.converters;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.CartItem;

public class MyConverter {

    public static CartItem toEntity(CartDTO cartDTO){
        CartItem cartItem = new CartItem();
        cartItem.setActive(1);
        cartItem.setAmount(cartDTO.getAmount());
        return cartItem;
    }

    public static CartDTO toDTO(CartItem cartItemEntity) {
        CartDTO cart = new CartDTO();
        cart.setId(cartItemEntity.getId());
        cart.setActive(1);
        cart.setAmount(cartItemEntity.getAmount());
        cart.setUserID(cartItemEntity.getUser().getId());
        cart.setProductID(cartItemEntity.getProduct().getId());
        cart.setProductName(cartItemEntity.getProduct().getName());
        cart.setProductImg(cartItemEntity.getProduct().getImg());
        cart.setProductPrice(cartItemEntity.getProduct().getPrice());
        return cart;
    }
}
