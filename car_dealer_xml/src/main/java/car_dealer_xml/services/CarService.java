package car_dealer_xml.services;

import car_dealer_xml.models.dtos.seeddtos.CarSeedDto;
import car_dealer_xml.models.dtos.viewdtos.CarViewRootDto;
import car_dealer_xml.models.dtos.viewdtos.CarViewRootDtoWithParts;
import car_dealer_xml.models.entities.Car;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface CarService {

    void seedCars(List<CarSeedDto> carSeedDtos);

    Car getRandomCar();

    CarViewRootDto getAllToyotaCars();

    CarViewRootDtoWithParts getCarsWithParts() throws JAXBException;

}
