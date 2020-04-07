package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.TownSeedDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.GlobalConstants.TOWN_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        TownSeedDto[] dtos = this.gson
                .fromJson(new FileReader(TOWN_FILE_PATH), TownSeedDto[].class);

        Arrays.stream(dtos)
                .forEach(townSeedDto -> {
                    if (this.validationUtil.isValid(townSeedDto)) {
                        if (this.townRepository.findByName(townSeedDto.getName()) == null) {

                            Town town = this.modelMapper
                                    .map(townSeedDto, Town.class);

                            this.townRepository.saveAndFlush(town);

                            sb.append(String.format("Successfully imported Town %s - %s",
                                    townSeedDto.getName(), townSeedDto.getPopulation()));
                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid town");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();

    }

    @Override
    public Town findByName(String name) {
        return this.townRepository.findByName(name);
    }
}
