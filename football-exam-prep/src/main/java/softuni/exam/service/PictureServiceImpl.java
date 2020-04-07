package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.PictureSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XMLParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.PICTURE_FILE_PATH;


@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XMLParser xmlParser;


    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              ModelMapper modelMapper, ValidatorUtil validatorUtil, XMLParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public String importPictures() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        PictureSeedRootDto pictureSeedRootDto = this.xmlParser
                .convertFromFile(PICTURE_FILE_PATH, PictureSeedRootDto.class);

        pictureSeedRootDto.getPictures()
                .forEach(pictureSeedDto -> {
                    if (this.validatorUtil.isValid(pictureSeedDto)) {
                        if (this.pictureRepository.findByUrl(pictureSeedDto.getUrl()) == null) {

                            Picture picture = this.modelMapper
                                    .map(pictureSeedDto, Picture.class);

                            sb.append("Successfully imported picture - ")
                            .append(picture.getUrl());
                            this.pictureRepository.saveAndFlush(picture);

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
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findByUrl(url);
    }


}
