package car_dealer.services;


import car_dealer.models.dtos.seedDtos.CustomerSeedDto;
import car_dealer.models.dtos.viewDtos.CustomerOrderedDto;
import car_dealer.models.dtos.viewDtos.CustomerSalesDto;
import car_dealer.models.entities.Customer;

import java.util.List;

public interface CustomerService  {

    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    Customer getRandomCustomer();

    List<CustomerOrderedDto> getAllCustomersOrderByBirthDateAscAndIsYoungDriver();

    List<CustomerSalesDto> getAllCustomersWithSales();

}
