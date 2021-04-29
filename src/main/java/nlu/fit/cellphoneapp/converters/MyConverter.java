package nlu.fit.cellphoneapp.converters;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.helper.StringHelper;

public class MyConverter {

    public static CartItem toCartEntity(CartDTO cartDTO){
        CartItem cartItem = new CartItem();
        cartItem.setId(cartDTO.getId());
        cartItem.setActive(1);
        cartItem.setAmount(cartDTO.getAmount());
        return cartItem;
    }
    //cho cập nhật
    public static CartItem toCartEntity(CartDTO cartDTO, CartItem oldCartItem){
        oldCartItem.setId(cartDTO.getId());
        oldCartItem.setActive(1);
        oldCartItem.setAmount(cartDTO.getAmount());
        return oldCartItem;
    }

    public static CartDTO toCartDTO(CartItem cartItemEntity) {
        CartDTO cart = new CartDTO();
        cart.setId(cartItemEntity.getId());
        cart.setActive(1);
        int amount = cartItemEntity.getAmount();
        cart.setAmount(amount);
        cart.setUserID(cartItemEntity.getUser().getId());
        cart.setProductID(cartItemEntity.getProduct().getId());
        cart.setProductName(cartItemEntity.getProduct().getName());
        cart.setProductImg(cartItemEntity.getProduct().getImg());
        double price = cartItemEntity.getProduct().getPrice();
        cart.setProductPrice(price);
        double totalPrice = amount*price;
        cart.setTotalPrice(totalPrice);
        return cart;
    }
}
