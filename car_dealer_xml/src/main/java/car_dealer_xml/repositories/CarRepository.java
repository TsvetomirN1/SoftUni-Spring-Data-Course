package car_dealer_xml.repositories;

import car_dealer_xml.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMakeAndModelAndTravelledDistance(String name,String model,Long distance);

    List<Car> getAllByMakeOrderByModelAscTravelledDistanceDesc(String make);

    @Query("select c from Car c where c.id >=1")
    List<Car> getAllCars();
}
