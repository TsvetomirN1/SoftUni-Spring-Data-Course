package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.SELLERS_FILE_PATH;


@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final XMLParser xmlParser;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper,
                             XMLParser xmlParser, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        SellerSeedRootDto sellerSeedRootDto = this.xmlParser
                .unmarshalFromFile(SELLERS_FILE_PATH, SellerSeedRootDto.class);

        sellerSeedRootDto.getSellers()
                .forEach(sellerSeedDto -> {
                    if (this.validationUtil.isValid(sellerSeedDto)) {
                        if (this.sellerRepository.findFirstByFirstNameAndLastNameAndRatingAndEmail(
                                sellerSeedDto.getFirstName(), sellerSeedDto.getLastName(),
                                sellerSeedDto.getRating(),sellerSeedDto.getEmail()) == null) {

                            Seller seller = this.modelMapper
                                    .map(sellerSeedDto, Seller.class);

                            sb.append(String.format("Successfully imported seller - %s - %s",
                                    sellerSeedDto.getLastName(), sellerSeedDto.getEmail()));

                            this.sellerRepository.saveAndFlush(seller);

                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid seller");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public Seller getSellerById(Long id) {
        return this.sellerRepository.findFirstById(id);
    }
}
