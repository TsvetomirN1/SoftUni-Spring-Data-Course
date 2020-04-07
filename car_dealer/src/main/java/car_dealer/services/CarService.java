package car_dealer.services;

import car_dealer.models.dtos.seedDtos.CarSeedDto;
import car_dealer.models.dtos.viewDtos.CarPartsDto;
import car_dealer.models.dtos.viewDtos.CarToyotaDto;
import car_dealer.models.entities.Car;

import java.util.List;


public interface CarService {

    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();

    List<CarToyotaDto> getAllToyotaOrderByMakeAscTravelledDistanceDesc();

    List<CarPartsDto> getAllCars();


}
