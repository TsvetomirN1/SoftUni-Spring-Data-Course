package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XMLParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.TEAM_FILE_PATH;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XMLParser xmlParser;
    private final PictureService pictureService;


    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil, XMLParser xmlParser, PictureService pictureService) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.pictureService = pictureService;
    }


    @Override

    public String importTeams() throws JAXBException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        TeamSeedRootDto teamSeedRootDto = this.xmlParser
                .convertFromFile(TEAM_FILE_PATH, TeamSeedRootDto.class);

        teamSeedRootDto.getTeams()
                .forEach(teamSeedDto -> {

                    if (this.validatorUtil.isValid(teamSeedDto)) {
                        if (teamRepository.findByName(teamSeedDto.getName()) == null) {

                            Team team = this.modelMapper.map(teamSeedDto, Team.class);

                            Picture picture = this.pictureService
                                    .getPictureByUrl(teamSeedDto.getPicture().getUrl());

                                team.setPicture(picture);

                            sb.append("Successfully imported - ")
                                    .append(team.getName());
                            this.teamRepository.saveAndFlush(team);

                        } else {
                            sb.append("Already in DB");
                        }
                    } else {

                        sb.append("Invalid team");
                    }

                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return Files.readString(Path.of(TEAM_FILE_PATH));
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

}
