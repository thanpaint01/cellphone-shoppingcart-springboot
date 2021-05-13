package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.CartItem;

import java.util.List;

public interface ICartService {
    boolean isInCart(int productID, int amount, int userID);
    CartItem insertIntoTable(CartItem cartItem);
    List<CartItem> getAllByUserID(int userID);
    CartItem getOneCartItem(int id);
    boolean deleteOne(int id);
    boolean removeAllByUserId(int userID);
    CartItem getOneByUserAndProduct(int userID, int productID);
}
