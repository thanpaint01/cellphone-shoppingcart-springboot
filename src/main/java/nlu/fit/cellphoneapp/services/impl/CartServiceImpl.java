package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.converters.MyConverter;
import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.repositories.ICartRepository;
import nlu.fit.cellphoneapp.repositories.IProductRepository;
import nlu.fit.cellphoneapp.repositories.IUserRepository;
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
    public CartDTO insertIntoTable(CartDTO cartDTO) {
        System.out.println("CartRequest: " + cartDTO);
        CartItem cartItemEntity = new CartItem();
        /*
            Ở đây cần tính toán lại số tiền tổng cộng cho giỏ hàng
            Tổng tiền = số lượng * đơn giá
            Số lượng ở đây là số lượng trong giỏ
         */
        //sau khi có được Entity thì ta nhờ repo lưu giùm
        if (cartDTO.getId() == 0) {//thêm mới
            System.out.println("Thêm mới");
            cartItemEntity = MyConverter.toEntity(cartDTO);
        } else {
            System.out.println("Cập nhật số lượng");
        }
        Product productItem = productRepo.getOne(cartDTO.getProductID());
        cartItemEntity.setProduct(productItem);
        cartItemEntity.setUser(userRepo.getOne(cartDTO.getUserID()));
        double totalPrice = productItem.getPrice() * cartDTO.getAmount();
        cartItemEntity.setTotalPrice(totalPrice);
        cartItemEntity = cartRepo.save(cartItemEntity);
        System.out.println(cartItemEntity);
        return MyConverter.toDTO(cartItemEntity);
    }
}
