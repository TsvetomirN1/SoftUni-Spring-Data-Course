package car_dealer_xml.services.impls;

import car_dealer_xml.models.dtos.seeddtos.SupplierSeedDto;
import car_dealer_xml.models.dtos.viewdtos.SupplierViewDto;
import car_dealer_xml.models.dtos.viewdtos.SupplierViewRootDto;
import car_dealer_xml.models.entities.Supplier;
import car_dealer_xml.repositories.SupplierRepository;
import car_dealer_xml.services.SupplierService;
import car_dealer_xml.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;


    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Random random) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }


    @Override
    public void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos) {
        supplierSeedDtos
                .forEach(supplierSeedDto -> {
                    if (this.validationUtil.isValid(supplierSeedDto)) {
                        if (this.supplierRepository
                                .findByName(supplierSeedDto.getName()) == null) {

                            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                            this.supplierRepository.saveAndFlush(supplier);

                        } else {
                            System.out.println("Already in database");
                        }
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
        long randomId = this.random.nextInt((int) supplierRepository.count()) + 1;

        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public SupplierViewRootDto getAllLocalSuppliers() {

        SupplierViewRootDto supplierViewRootDto = new SupplierViewRootDto();

        List<SupplierViewDto> supplierViewDtos = supplierRepository.getAllByImporterFalse()
                .stream().map(s -> {
                    SupplierViewDto supplierViewDto = this.modelMapper.map(s, SupplierViewDto.class);
                    supplierViewDto.setPartsCount(s.getParts().size());
                    return supplierViewDto;
                })
                .collect(Collectors.toList());

        supplierViewRootDto.setSuppliers(supplierViewDtos);

        return supplierViewRootDto;

    }
}
