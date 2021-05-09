package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.ICartRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    ICartRepository cartRepo;
    @Autowired
    IProductRepository productRepo;
    @Autowired
    IUserRepository userRepo;


    /*
        Phương thức kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa ? (true/false)
     */
    @Override
    public boolean isInCart(int productID, int amount, int userID) {
        List<CartItem> items = cartRepo.getAllByUser(userRepo.getOne(userID));
        for (CartItem item : items) {
            if (item.getProduct().getId() == productID) return true;
        }
        return false;
    }

    /*
        Phương thức insert vào bảng này có thể sử dụng cho việc cập nhật thông tin bảng
        Ví dụ: cập nhật số lượng sản phẩm trong giỏ hàng
        Vì vậy phải check id cart trước khi insert, liệu đã tồn tại sản phẩm đó hay chưa, và còn số lượng để thêm hay không
     */
    @Override
    public CartItem insertIntoTable(CartItem cartItem) {
        CartItem cartItemEntity = cartItem;
        /*
            Ở đây cần tính toán lại số tiền tổng cộng cho giỏ hàng
            Tổng tiền = số lượng * đơn giá
            Số lượng ở đây là số lượng trong giỏ
         */
        //sau khi có được Entity thì ta nhờ repo lưu giùm
        if (cartItem.getId() == 0) {//thêm mới
            System.out.println("Thêm mới");
            cartItemEntity = cartRepo.save(cartItemEntity);
        } else {
            System.out.println("Cập nhật số lượng");
            CartItem oldCartItem = cartRepo.getOne(cartItem.getId());
            oldCartItem.setAmount(cartItem.getAmount());
            double totalPrice = oldCartItem.getProduct().getPrice() * oldCartItem.getAmount();
            cartItemEntity.setTotalPrice(totalPrice);
            cartItemEntity = cartRepo.save(cartItemEntity);
            System.out.println(cartItemEntity);
        }
        return cartItemEntity;
    }

    @Override
    public List<CartItem> getAllByUserID(int userID) {
        List<CartItem> cartItemList = cartRepo.getAllByUser(userRepo.getOne(userID));
        return cartItemList;
    }

    @Override
    public CartItem getOneCartItem(int id) {
        return cartRepo.getOne(id);
    }

    @Override
    public boolean deleteOne(int id) {
        System.out.println("CartID for service delete =" + id);
        if (id != 0) {
            cartRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAllByUserId(int userID) {
        User user = userRepo.getOne(userID);
        System.out.println("Remove All CartItem Of User" + userID);
           cartRepo.deleteAllByUser_Id(userID);
        System.out.println("After remove");
        for (CartItem c : user.getCartItems()) {
            System.out.println("productInCart=" + c.getProduct().toString());
        }
        user.getCartItems().clear();
        System.out.println("after clear");
        for (CartItem c : user.getCartItems()) {
            System.out.println(c.getProduct().toString());
        }
        return (userRepo.getOne(userID).getCartItems().size() == 0 ? true : false);
    }

}
