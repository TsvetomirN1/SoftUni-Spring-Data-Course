package car_dealer.services.impl;

import car_dealer.models.dtos.seedDtos.CarSeedDto;
import car_dealer.models.dtos.viewDtos.CarPartsDto;
import car_dealer.models.dtos.viewDtos.CarToyotaDto;
import car_dealer.models.entities.Car;
import car_dealer.repositories.CarRepository;
import car_dealer.services.CarService;
import car_dealer.services.PartService;
import car_dealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper,
                          ValidationUtil validationUtil, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
    }


    @Override
    public void seedCars(CarSeedDto[] carSeedDtos) {

        if (this.carRepository.count() != 0) {
            return;
        }

        Arrays.stream(carSeedDtos)
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        Car car = this.modelMapper
                                .map(carSeedDto, Car.class);

                        car.setParts(this.partService.getRandomParts());


                        this.carRepository.saveAndFlush(car);

                    } else {
                        this.validationUtil.getViolations(carSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });

    }

    @Override
    public Car getRandomCar() {

        Random random = new Random();
        long index = random.nextInt((int) this.carRepository.count()) + 1;

        return this.carRepository.getOne(index);

    }

    @Override
    public List<CarToyotaDto> getAllToyotaOrderByMakeAscTravelledDistanceDesc() {

        return this.carRepository.getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(c -> this.modelMapper.map(c, CarToyotaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarPartsDto> getAllCars() {

        return this.carRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, CarPartsDto.class))
                .collect(Collectors.toList());
    }
}
