package product_shop_xml.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import product_shop_xml.models.entities.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByFirstNameAndLastName(String first,String last );

    @Query(value = "SELECT u FROM User as u JOIN u.sold as p WHERE p.buyer IS NOT NULL" +
            " ORDER BY u.lastName, u.firstName")
    Set<User> findUsersWithSoldProducts();
}
