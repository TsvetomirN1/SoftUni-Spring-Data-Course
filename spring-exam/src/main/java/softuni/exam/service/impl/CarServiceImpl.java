package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.GlobalConstants.CARS_FILE_PATH;


@Service
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper,
                          Gson gson, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {

        StringBuilder sb = new StringBuilder();

        CarSeedDto[] dtos = this.gson
                .fromJson(new FileReader(CARS_FILE_PATH), CarSeedDto[].class);

        Arrays.stream(dtos)
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        if (this.carRepository.findByMakeAndModelAndKilometers(
                                carSeedDto.getMake(), carSeedDto.getModel(), carSeedDto.getKilometers()) == null) {

                            Car car = this.modelMapper
                                    .map(carSeedDto, Car.class);

                            LocalDate localDate = LocalDate.parse(carSeedDto.getRegisteredOn(),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                            car.setRegisteredOn(localDate);

                            this.carRepository.saveAndFlush(car);

                            sb.append(String.format("Successfully imported car - %s %s",
                                    carSeedDto.getMake(), carSeedDto.getModel()));
                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid car");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }


    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        return null;
    }

    @Override
    public Car getCarById(Long id) {
        return this.carRepository.findFirstById(id);
    }

}
