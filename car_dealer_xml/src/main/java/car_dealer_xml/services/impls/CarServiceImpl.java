package car_dealer_xml.services.impls;

import car_dealer_xml.models.dtos.seeddtos.CarSeedDto;
import car_dealer_xml.models.dtos.viewdtos.*;
import car_dealer_xml.models.entities.Car;
import car_dealer_xml.repositories.CarRepository;
import car_dealer_xml.services.CarService;
import car_dealer_xml.services.PartService;
import car_dealer_xml.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@Transactional
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;
    private final Random random;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService, Random random) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
        this.random = random;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {

        carSeedDtos
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        if (this.carRepository.findByMakeAndModelAndTravelledDistance(
                                carSeedDto.getMake(), carSeedDto.getModel(),
                                carSeedDto.getTravelledDistance()) == null) {

                            Car car = this.modelMapper.map(carSeedDto, Car.class);
                            car.setParts(this.partService.getRandomParts());

                            this.carRepository.saveAndFlush(car);

                        } else {
                            System.out.println("Already in database");
                        }

                    } else {
                        this.validationUtil
                                .getViolations(carSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }

                });

    }

    @Override
    public Car getRandomCar() {

        long randomId = this.random.nextInt((int) this.carRepository.count()) + 1;

        return this.carRepository.getOne(randomId);


    }

    @Override
    public CarViewRootDto getAllToyotaCars() {

        CarViewRootDto carViewRootDto = new CarViewRootDto();

        List<CarViewDto> carViewDtos = this.carRepository
                .getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car, CarViewDto.class))
                .collect(Collectors.toList());

        carViewRootDto.setCars(carViewDtos);

        return carViewRootDto;
    }

    @Override
    public CarViewRootDtoWithParts getCarsWithParts() throws JAXBException {

        List<Car> cars = carRepository.getAllCars();
        List<CarViewDtoWithParts> carViewDtoWithParts = new ArrayList<>();
        for (Car c : cars) {
            List<PartViewDto> partViewDtos = c.getParts().stream()
                    .map(p -> modelMapper.map(p, PartViewDto.class)).collect(Collectors.toList());
            PartViewRootDto partViewRootDto = new PartViewRootDto();

            partViewRootDto.setParts(partViewDtos);

            CarViewRootDtoWithParts carViewRootDtoWithParts = new CarViewRootDtoWithParts();
            CarViewDtoWithParts carViewDtoWithPart = new CarViewDtoWithParts(c.getMake(), c.getModel(), c.getTravelledDistance(), partViewRootDto);
            carViewDtoWithParts.add(carViewDtoWithPart);
        }
        CarViewRootDtoWithParts carViewRootDtoWithParts = new CarViewRootDtoWithParts();
        carViewRootDtoWithParts.setCars(carViewDtoWithParts);


        return carViewRootDtoWithParts;
    }
}


