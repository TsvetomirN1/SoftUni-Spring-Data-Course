package car_dealer.services;


import car_dealer.models.dtos.seedDtos.SupplierSeedDto;
import car_dealer.models.dtos.viewDtos.LocalSuppliersDto;
import car_dealer.models.entities.Supplier;

import java.util.List;

public interface SupplierService {

    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);

    Supplier getRandomSupplier();

    List<LocalSuppliersDto> getAllLocalSuppliers();
}
