package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private String content;
    private Integer stars;
    private Integer active;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Comment> comments;
}
