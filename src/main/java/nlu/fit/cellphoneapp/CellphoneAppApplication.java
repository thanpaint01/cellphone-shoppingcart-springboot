package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.DTOs.CartItemRequest;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.repositories.ExtendedRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@SpringBootApplication
//@ComponentScan(basePackages = "nlu.fit.cellphoneapp.specification")
public class CellphoneAppApplication {

    public static void main(String[] args) {
        Collection<Integer> listCurrent = new HashSet<Integer>();
        listCurrent.add(2);
        listCurrent.add(3);
        listCurrent.add(4);
        listCurrent.add(5);
        listCurrent.add(6);
        Collection<Integer> listUser = new HashSet<Integer>();
        listUser.add(2);
        listUser.add(3);
        listUser.add(4);

        Collection<Integer> collectionNewCartItem = new HashSet<Integer>();
        int i = 0;
        while(i < listCurrent.size()) {
            boolean isEquals = false;
            int cq = 0;
            System.out.println("cq======"+ (cq = (int) listCurrent.toArray()[i]));
            for (Integer c : listUser) {
                System.out.printf("%d , %d ==> %s", c, cq, c ==cq);
                if ( cq==c) {
                    System.out.println("cq = "+cq);
                    c = new Integer(c+cq);
                    isEquals = true;
                }
                System.out.printf(" ====> %d\n", c);
            }
            i++;
            System.out.println("equals == "+isEquals);
            if(isEquals == false){
                System.out.println("in false");
                collectionNewCartItem.add(new Integer(cq));
            }
            System.out.println();
        }

        for (Integer cqnew: collectionNewCartItem) {
            System.out.println("new cart item= "+cqnew);
            listUser.add(cqnew);
        }

        for (Integer c: listUser) {
            System.out.println("list user = "+c);
        }



        SpringApplication.run(CellphoneAppApplication.class, args);
    }

}
