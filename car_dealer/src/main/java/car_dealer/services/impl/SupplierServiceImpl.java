package car_dealer.services.impl;

import car_dealer.models.dtos.seedDtos.SupplierSeedDto;
import car_dealer.models.dtos.viewDtos.LocalSuppliersDto;
import car_dealer.models.entities.Supplier;
import car_dealer.repositories.SupplierRepository;
import car_dealer.services.SupplierService;
import car_dealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {

        this.modelMapper.addMappings(new PropertyMap<SupplierSeedDto, Supplier>() {
            @Override
            protected void configure() {
                boolean isImporter = false;

                if (source.isImporter()) {
                    isImporter = true;
                }
                map().setImporter(isImporter);
            }
        });

        if (this.supplierRepository.count() != 0) {
            return;
        }

        Arrays.stream(supplierSeedDtos)
                .forEach(supplierSeedDto -> {
                    if (this.validationUtil.isValid(supplierSeedDto)) {
                        Supplier supplier = this.modelMapper
                                .map(supplierSeedDto, Supplier.class);

                        this.supplierRepository.saveAndFlush(supplier);
                    } else {
                        this.validationUtil.getViolations(supplierSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {

        Random random = new Random();
        long randomId = random
                .nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public List<LocalSuppliersDto> getAllLocalSuppliers() {

        return this.supplierRepository.getAllByImporterFalse()
                .stream().map(s -> {
                    LocalSuppliersDto ls = this.modelMapper.map(s, LocalSuppliersDto.class);
                    ls.setPartsCount(s.getParts().size());
                    return ls;
                })
                .collect(Collectors.toList());
    }
}
