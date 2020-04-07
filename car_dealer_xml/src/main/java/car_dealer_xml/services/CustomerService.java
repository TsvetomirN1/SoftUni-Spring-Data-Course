package car_dealer_xml.services;

import car_dealer_xml.models.dtos.seeddtos.CustomerSeedDto;
import car_dealer_xml.models.dtos.viewdtos.CustomerViewRootDto;
import car_dealer_xml.models.entities.Customer;

import java.util.List;

public interface CustomerService {

    void seedCustomers(List<CustomerSeedDto> customerSeedDtos);

    Customer getRandomCustomer();

    CustomerViewRootDto getAllOrderCustomers();


}
