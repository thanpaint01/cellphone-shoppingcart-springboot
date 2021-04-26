package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.dto.CartDTO;

public interface ICartService {
    boolean isInCart(int productID, int amount, int userID);
    CartDTO insertIntoTable(CartDTO cartDTO);
}
