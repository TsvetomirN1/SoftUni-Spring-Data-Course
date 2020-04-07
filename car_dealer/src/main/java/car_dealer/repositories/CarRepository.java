package car_dealer.repositories;

import car_dealer.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> getAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
