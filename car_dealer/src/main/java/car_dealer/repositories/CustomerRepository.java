package car_dealer.repositories;

import car_dealer.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("select c from car_dealer.models.entities.Customer as c where c.sales.size > 0 ")
    List<Customer> getAllCustomersWithSales();
}
