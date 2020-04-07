package car_dealer_xml.services;

import car_dealer_xml.models.dtos.seeddtos.PartSeedDto;
import car_dealer_xml.models.entities.Part;

import java.util.List;
import java.util.Set;

public interface PartService {

    void seedParts(List<PartSeedDto> partSeedDtos);

    Set<Part> getRandomParts();
}
