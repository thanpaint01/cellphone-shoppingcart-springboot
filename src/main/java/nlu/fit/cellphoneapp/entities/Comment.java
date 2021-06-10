package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    Review review;
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
    String content;
    Integer active;
}
