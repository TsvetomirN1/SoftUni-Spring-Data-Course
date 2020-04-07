package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.GlobalConstants.PICTURE_FILE_PATH;


@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CarService carService;


    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper,
                              Gson gson, ValidationUtil validationUtil, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {

        StringBuilder sb = new StringBuilder();

        PictureSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PICTURE_FILE_PATH), PictureSeedDto[].class);

        Arrays.stream(dtos)
                .forEach(pictureSeedDto -> {
                    if (this.validationUtil.isValid(pictureSeedDto)) {
                        if (this.pictureRepository.findByName(
                                pictureSeedDto.getName()) == null) {

                            Picture picture = this.modelMapper
                                    .map(pictureSeedDto, Picture.class);

                            LocalDateTime localDateTime = LocalDateTime.parse(pictureSeedDto.getDateAndTime(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                            Car car = this.carService.getCarById(pictureSeedDto.getCar());

                            picture.setDateAndTime(localDateTime);
                            if (car!=null){
                                picture.setCar(car);
                            }

                            this.pictureRepository.saveAndFlush(picture);

                            sb.append(String.format("Successfully imported picture - %s ",
                                    pictureSeedDto.getName()));
                        } else {
                            sb.append("Already in DB");
                        }
                    } else {
                        sb.append("Invalid picture");
                    }

                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public List<Picture> findByCar_Id(Long id) {
        return this.pictureRepository.findByCar_Id(id);
    }
}
