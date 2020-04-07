package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PassengerSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.GlobalConstants.PASSENGERS_FILE_PATH;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownService townService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, TownService townService) {
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {

        StringBuilder sb = new StringBuilder();

        PassengerSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PASSENGERS_FILE_PATH), PassengerSeedDto[].class);

        Arrays.stream(dtos)
                .forEach(passengerSeedDto -> {
                    if (this.validationUtil.isValid(passengerSeedDto)) {
                        if (this.passengerRepository.findByEmail(passengerSeedDto.getEmail()) == null) {

                            Passenger passenger = this.modelMapper
                                    .map(passengerSeedDto, Passenger.class);

                            Town town = this.townService.findByName(passengerSeedDto.getTown());

                            passenger.setTown(town);

                            this.passengerRepository.saveAndFlush(passenger);

                            sb.append(String.format("Successfully imported Passenger %s - %s",
                                    passengerSeedDto.getLastName(), passengerSeedDto.getEmail()));
                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid Passenger");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();

    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {

        StringBuilder sb = new StringBuilder();

        this.passengerRepository.findAllByOrderByTicketsDescEmail()
                .forEach(passenger -> {

                    sb.append(String.format("Passenger %s %s\n" +
                                    "Email - %s\n" +
                                    "Phone - %s\n" +
                                    "Number of tickets - %d\n",
                            passenger.getFirstName(), passenger.getLastName(),
                            passenger.getEmail(), passenger.getPhoneNumber(),
                            passenger.getTickets().size()
                    )).append(System.lineSeparator());

                });

        return sb.toString();
    }

    @Override
    public Passenger findByEmail(String email) {
        return this.passengerRepository.findByEmail(email);
    }
}
