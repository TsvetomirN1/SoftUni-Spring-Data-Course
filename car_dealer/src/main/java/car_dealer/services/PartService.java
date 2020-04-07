package car_dealer.services;

import car_dealer.models.dtos.seedDtos.PartsSeedDto;
import car_dealer.models.entities.Part;

import java.util.Set;

public interface PartService {

    void seedParts(PartsSeedDto[] partsSeedDtos);

     Set<Part> getRandomParts();
}
