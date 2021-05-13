package nlu.fit.cellphoneapp.repositories.interfaces;

import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.receiver.BrandProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByActive(int active);

    @Query(value = "SELECT b.id AS id,b.`name` AS brandname,b.logo AS img,b.active AS active,SUM(od.total_price) AS profit FROM brand b " +
            "JOIN product p ON p.brand_id=b.id " +
            "JOIN `order_detail` od ON od.product_id=p.id " +
            "JOIN `order` AS o ON o.id=od.order_id " +
            "WHERE o.`status`='Giao hàng thành công' AND o.created_date > DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) - interval (dayofmonth(curdate()) - 1) day - interval 6 month " +
            "GROUP BY brand_id " +
            "ORDER BY profit " +
            "LIMIT 5", nativeQuery = true)
    List<BrandProfit> getTop5Profit();
}
