package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
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
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> cartItems = new ArrayList<>();
    //ManyToMany user_role
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public static boolean validName(String name) {
        String expression = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        return name.matches(expression);
    }

    public static boolean validPassword(String password) {
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return password.matches(expression);
    }

    public static boolean validUsername(String username) {
        return username.matches("^[a-zA-Z0-9._-]{8,}$");
    }

    public static boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
