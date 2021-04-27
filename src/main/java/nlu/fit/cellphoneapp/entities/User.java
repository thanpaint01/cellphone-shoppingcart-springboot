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
    public enum ACCESS {
        CONSUMEER(0), ADMIN(1);
        private final int value;

        ACCESS(int value) {
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Cart cart;
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

    public static boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static boolean validGender(int gender) {
        return gender == 1 || gender == 2;
    }
    public static boolean validGender(String gender) {
        return gender.equals("Nam") || gender.equals("Nữ") ;
    }
}
