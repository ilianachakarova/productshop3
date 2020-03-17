package softuni.productshop3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productshop3.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User as u join u.productsSold as p where u.productsSold.size >0 and p.buyer is not null " +
            "group by u.id order by u.lastName asc, u.firstName asc ")
    List<User>getUsersByProductAtLeastOne();
//    select u.first_name, u.last_name,
//    p.name from users as u join products as p
//    on u.id = p.seller_id
//    group by u.id
//    order by count(p.id) desc , u.last_name asc
    @Query("select u from User as u join u.productsSold as p where p.buyer is not null " +
            "group by u.id order by u.productsSold.size desc, u.lastName asc")
    List<User>getUsersWithAtLeastOneProductSold();
}
