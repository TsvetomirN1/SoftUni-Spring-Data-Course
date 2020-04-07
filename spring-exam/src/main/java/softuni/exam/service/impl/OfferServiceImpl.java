package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.PictureService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final XMLParser xmlParser;
    private final ValidationUtil validationUtil;
    private final CarService carService;
    private final SellerService sellerService;
    private final PictureService pictureService;

    public OfferServiceImpl(OfferRepository offerRepository,
                            ModelMapper modelMapper, XMLParser xmlParser, ValidationUtil validationUtil, CarService carService, SellerService sellerService, PictureService pictureService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.carService = carService;
        this.sellerService = sellerService;
        this.pictureService = pictureService;
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.OFFERS_FILE_PATH));
    }

    @Override

    public String importOffers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();


        OfferSeedRootDto offerSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.OFFERS_FILE_PATH, OfferSeedRootDto.class);


        offerSeedRootDto.getOfferSeedDtos()
                .forEach(offerSeedDto -> {
                    if (this.validationUtil.isValid(offerSeedDto)) {

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd' 'HH:mm:ss");

                        LocalDateTime localDateTime = LocalDateTime
                                .parse(offerSeedDto.getAddedOn(), dateTimeFormatter);

                        if (this.offerRepository.findByDescriptionAndAddedOn(
                                offerSeedDto.getDescription(), localDateTime) == null) {

                            Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);

                            Car car = this.carService.getCarById(offerSeedDto.getCarDto().getId());

                            Seller seller = this.sellerService.getSellerById(offerSeedDto
                                    .getSellerDto().getId());

                            if (car != null && seller != null) {

                                offer.setAddedOn(localDateTime);
                                offer.setCar(car);
                                offer.setSeller(seller);

                                offer.setPictures(this.pictureService.findByCar_Id(car.getId()));

                                sb.append(String.format(
                                        "Successfully import seller %s - %s",
                                        offer.getAddedOn().toString(),
                                        offer.getHasGoldStatus()
                                ));

                                this.offerRepository.saveAndFlush(offer);

                            } else {
                                sb.append("Invalid offer");
                            }
                        } else {
                            sb.append("Already in DB");
                        }

                    } else {
                        sb.append("Invalid offer");
                    }
                    sb.append(System.lineSeparator());

                });
        return sb.toString();
    }
}




