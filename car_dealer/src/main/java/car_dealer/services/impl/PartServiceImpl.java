package car_dealer.services.impl;

import car_dealer.models.dtos.seedDtos.PartsSeedDto;
import car_dealer.models.entities.Part;
import car_dealer.repositories.PartRepository;
import car_dealer.services.PartService;
import car_dealer.services.SupplierService;
import car_dealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;


    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
    }


    @Override
    public void seedParts(PartsSeedDto[] partsSeedDtos) {

        if (this.partRepository.count() != 0) {
            return;
        }

        Arrays.stream(partsSeedDtos)
                .forEach(partSeedDto -> {
                    if (this.validationUtil.isValid(partSeedDto)) {
                        Part part = this.modelMapper
                                .map(partSeedDto, Part.class);

                        part.setSupplier(this.supplierService.getRandomSupplier());

                        this.partRepository.saveAndFlush(part);

                    } else {
                        this.validationUtil.getViolations(partSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Set<Part> getRandomParts() {

        Set<Part> parts = new HashSet<>();
        Random random = new Random();
        int count = random.nextInt(10) + 10;

        for (int i = 0; i < count; i++) {
            long index = random.nextInt((int) this.partRepository.count()) + 1;
            Part part = this.partRepository.getOne(index);

            boolean notContain = true;

            for (Part p : parts) {
                if (p.getId() == part.getId()) {
                    notContain = false;
                    break;
                }
            }

            if (notContain) {
                parts.add(part);
            }
        }

        System.out.println();
        return parts;

    }
}



