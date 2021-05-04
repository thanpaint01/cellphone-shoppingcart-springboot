package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.CartItem;

import java.util.List;

public interface ICartService {
    boolean isInCart(int productID, int amount, int userID);
    CartDTO insertIntoTable(CartDTO cartDTO);
    List<CartDTO> getAllByUserID(int userID);
    CartItem getOneCartItem(int id);
    boolean deleteOne(int id);
}
