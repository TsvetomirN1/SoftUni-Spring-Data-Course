package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TicketSeedRootDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static softuni.exam.constants.GlobalConstants.TICKETS_FILE_PATH;


@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XMLParser xmlParser;
    private final TownService townService;
    private final PassengerService passengerService;
    private final PlaneService planeService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             ModelMapper modelMapper, ValidationUtil validationUtil, XMLParser xmlParser, TownService townService, PassengerService passengerService, PlaneService planeService) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
        this.passengerService = passengerService;
        this.planeService = planeService;
    }


    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        TicketSeedRootDto ticketSeedRootDto = this.xmlParser
                .unmarshalFromFile(TICKETS_FILE_PATH, TicketSeedRootDto.class);

        ticketSeedRootDto.getTickets()
                .forEach(ticketSeedDto -> {
                    if (this.validationUtil.isValid(ticketSeedDto)) {
                        if (this.ticketRepository.findBySerialNumber(
                                ticketSeedDto.getSerialNumber()) == null) {


                            Ticket ticket = this.modelMapper
                                    .map(ticketSeedDto, Ticket.class);

                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                                    .ofPattern("yyyy-MM-dd' 'HH:mm:ss");

                            LocalDateTime localDateTime = LocalDateTime
                                    .parse(ticketSeedDto.getTakeoff(), dateTimeFormatter);

                            Town from = this.townService.findByName(ticketSeedDto.getFromTown().getName());
                            Town to = this.townService.findByName(ticketSeedDto.getFromTown().getName());
                            Passenger passenger = this.
                                    passengerService.findByEmail(ticketSeedDto.getPassenger().getEmail());
                            Plane plane = this.planeService.
                                    findByRegisterNumber(ticketSeedDto.getPlane().getRegNumber());

                            ticket.setTakeoff(localDateTime);
                            ticket.setFromTown(from);
                            ticket.setToTown(to);
                            ticket.setPassenger(passenger);
                            ticket.setPlane(plane);

                            this.ticketRepository.saveAndFlush(ticket);

                            sb.append(String.format("Successfully imported Ticket %s - %s",
                                    ticketSeedDto.getFromTown().getName(), ticketSeedDto.getToTown().getName()));

                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid Ticket");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();


    }
}
