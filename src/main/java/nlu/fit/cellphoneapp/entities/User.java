package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Pattern;

@Entity
@Table(name = "user")
@Getter
@Setter

public class User {
    public static final String SESSION = "currentUser";

    public enum ROLE {
        CONSUMEER(1), ADMIN(2);
        private final int value;

        ROLE(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum ACTIVE {
        ACTIVE(1), INACTIVE(2), UNVERTIFIED(-1);
        private final int value;

        ACTIVE(int constValue) {
            this.value = constValue;
        }

        public int value() {
            return value;
        }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String avatar;
    private String phone;
    private String address;
    private String gender;
    private Date birth;
    private String key;
    @Column(name = "expired_key")
    private Date expiredKey;
    private int active;
    private int role;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<CartItem> cartItems = new HashSet<>();
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Order> orders = new HashSet<>();


    public static boolean validName(String name) {
        String expression = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        return name.matches(expression);
    }

    public static boolean validPassword(String password) {
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return password.matches(expression);
    }

    public static boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static String toStringGender(int gender) {
        return gender == 1 ? "Nam" : "Nữ";
    }

    public static boolean validGender(int gender) {
        return gender == 1 || gender == 2;
    }

    public static boolean validGender(String gender) {
        return gender.equals("Nam") || gender.equals("Nữ");
    }

    public double getTotalPrice() {
        double rs = 0.0;
        for (CartItem c : cartItems) {
            rs += (c.getAmount() * c.getProduct().getPrice());
        }
        return rs;
    }

    public boolean checkCartItemExist(int productID){
        System.out.println("cartItemID for user check: "+productID);
        for (CartItem c: cartItems) {
            if(c.getProduct().getId()==productID){
                return true;
            }
        }
        return false;
    }

    public static boolean checkUserSession(HttpSession session){
        if(null != (User) session.getAttribute(SESSION)) return true;
        return false;
    }



}
