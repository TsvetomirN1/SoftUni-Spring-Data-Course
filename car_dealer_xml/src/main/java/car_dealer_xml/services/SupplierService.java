package car_dealer_xml.services;

import car_dealer_xml.models.dtos.seeddtos.SupplierSeedDto;
import car_dealer_xml.models.dtos.viewdtos.SupplierViewRootDto;
import car_dealer_xml.models.entities.Supplier;

import java.util.List;
import java.util.Random;

public interface SupplierService {

    void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos);

    Supplier getRandomSupplier();

    SupplierViewRootDto getAllLocalSuppliers();

}
