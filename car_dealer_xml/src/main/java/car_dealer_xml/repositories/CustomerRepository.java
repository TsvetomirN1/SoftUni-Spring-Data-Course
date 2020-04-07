package car_dealer_xml.repositories;

import car_dealer_xml.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByNameAndBirthDate(String name, LocalDateTime dateTime);

    @Query("SELECT c FROM Customer as c " +
            "ORDER BY c.birthDate,c.youngDriver")
    List<Customer> findAllByBirthDateAndYoungDriver();


}
