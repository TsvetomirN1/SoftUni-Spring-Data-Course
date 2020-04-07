package car_dealer_xml.controllers;

import car_dealer_xml.constants.GlobalConstants;
import car_dealer_xml.models.dtos.seeddtos.CarSeedRootDto;
import car_dealer_xml.models.dtos.seeddtos.CustomerSeedRootDto;
import car_dealer_xml.models.dtos.seeddtos.PartSeedRootDto;
import car_dealer_xml.models.dtos.seeddtos.SupplierSeedRootDto;
import car_dealer_xml.models.dtos.viewdtos.CarViewRootDto;
import car_dealer_xml.models.dtos.viewdtos.CarViewRootDtoWithParts;
import car_dealer_xml.models.dtos.viewdtos.CustomerViewRootDto;
import car_dealer_xml.models.dtos.viewdtos.SupplierViewRootDto;
import car_dealer_xml.services.*;
import car_dealer_xml.utils.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class AppController implements CommandLineRunner {

    private final XMLParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;


    @Autowired
    public AppController(XMLParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.seedSuppliers();
//        this.seedParts();
//        this.seedCars();
//        this.seedCustomers();
//        this.seedSales();

//        query 1
        this.orderedCustomers();

//        query 2
//        this.writeToyotaCars();

//        query 3
//        this.writeLocalSuppliers();

//        query 4
//        this.writeCarsWithParts();

//
    }


    private void writeCarsWithParts() throws JAXBException {

       CarViewRootDtoWithParts carViewRootDtoWithParts =  this.carService.getCarsWithParts();

        this.xmlParser.marshalToFile(GlobalConstants.QUERY4,carViewRootDtoWithParts);

    }

    private void writeLocalSuppliers() throws JAXBException {

        SupplierViewRootDto supplierViewRootDto = this.supplierService.getAllLocalSuppliers();
        this.xmlParser.marshalToFile(GlobalConstants.QUERY3,supplierViewRootDto);

    }

    private void writeToyotaCars() throws JAXBException {

        CarViewRootDto carViewRootDto = this.carService.getAllToyotaCars();
        this.xmlParser
                .marshalToFile(GlobalConstants.QUERY2,carViewRootDto);

    }

    private void orderedCustomers() throws JAXBException {

        CustomerViewRootDto customerViewRootDto = this.customerService.getAllOrderCustomers();
        this.xmlParser
                .marshalToFile(GlobalConstants.QUERY1,customerViewRootDto);
    }

    private void seedSales() {

        this.saleService.seedSales();

    }

    private void seedCustomers() throws JAXBException, FileNotFoundException {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.CUSTOMERS_FILE_PATH, CustomerSeedRootDto.class);

        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());
    }

    private void seedCars() throws JAXBException, FileNotFoundException {

        CarSeedRootDto carSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.CARS_FILE_PATH, CarSeedRootDto.class);

        this.carService.seedCars(carSeedRootDto.getCars());
    }

    private void seedParts() throws JAXBException, FileNotFoundException {
        PartSeedRootDto partSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.PARTS_FILE_PATH, PartSeedRootDto.class);


        this.partService.seedParts(partSeedRootDto.getParts());


    }

    private void seedSuppliers() throws JAXBException, FileNotFoundException {

        SupplierSeedRootDto supplierSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.SUPPLIERS_FILE_PATH, SupplierSeedRootDto.class);

        this.supplierService.seedSuppliers(supplierSeedRootDto.getSuppliers());

    }
}
