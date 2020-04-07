package car_dealer.controllers;


import car_dealer.models.dtos.seedDtos.CarSeedDto;
import car_dealer.models.dtos.seedDtos.CustomerSeedDto;
import car_dealer.models.dtos.seedDtos.PartsSeedDto;
import car_dealer.models.dtos.seedDtos.SupplierSeedDto;
import car_dealer.models.dtos.viewDtos.CarPartsDto;
import car_dealer.models.dtos.viewDtos.CarToyotaDto;
import car_dealer.models.dtos.viewDtos.CustomerOrderedDto;
import car_dealer.services.*;
import car_dealer.utils.FileUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static car_dealer.constants.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {


    private final Gson gson;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final FileUtil fileUtil;


    @Autowired
    public AppController(Gson gson, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil) {
        this.gson = gson;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
    }


    @Override
    public void run(String... args) throws Exception {

        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.createSales();

//        query 1
        writeOrderedCustomers();

//        query 2
        writeToyotaCars();

//        query 3
        writeLocalSuppliers();

//        query 4
        writeCarsWithParts();

//        query 5
        writeCustomerSales();
    }

    private void writeCustomerSales() throws IOException {

        this.fileUtil.write(this.gson.toJson(this.customerService.getAllCustomersWithSales().toArray()),
               QUERY5);
    }

    private void writeCarsWithParts() throws IOException {

        List<CarPartsDto> dtos = this.carService.getAllCars();
        String carsJson = this.gson.toJson(dtos);
        this.fileUtil.write(carsJson, QUERY4);

    }

    private void writeLocalSuppliers() throws IOException {

        this.fileUtil.write(this.gson.toJson(this.supplierService
                        .getAllLocalSuppliers().toArray()),
                QUERY3);
    }

    private void writeToyotaCars() throws IOException {

        List<CarToyotaDto> cars = carService.getAllToyotaOrderByMakeAscTravelledDistanceDesc();

        String carsJson = this.gson.toJson(cars);
        this.fileUtil.write(carsJson, QUERY2);

    }

    private void writeOrderedCustomers() throws IOException {

        List<CustomerOrderedDto> dtos = this.customerService
                .getAllCustomersOrderByBirthDateAscAndIsYoungDriver()
                .stream()
                .map(c -> {
                    c.setBirthDate(c.getBirthDate());
                    c.setSales(new ArrayList<>());
                    return c;
                })
                .collect(Collectors.toList());

        String json = this.gson.toJson(dtos);

        this.fileUtil.write(json, QUERY1);

    }

    private void createSales() {

        this.saleService.createSales();
    }

    private void seedCustomers() throws FileNotFoundException {

        CustomerSeedDto[] dtos = this.gson
                .fromJson(new FileReader(CUSTOMERS_FILE_PATH),
                        CustomerSeedDto[].class);

        this.customerService.seedCustomers(dtos);
    }

    private void seedCars() throws FileNotFoundException {

        CarSeedDto[] dtos = this.gson
                .fromJson(new FileReader(CARS_FILE_PATH),
                        CarSeedDto[].class);

        this.carService.seedCars(dtos);

    }

    private void seedParts() throws FileNotFoundException {

        PartsSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PARTS_FILE_PATH),
                        PartsSeedDto[].class);

        this.partService.seedParts(dtos);

    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] dtos = this.gson
                .fromJson(new FileReader(SUPPLIERS_FILE_PATH),
                        SupplierSeedDto[].class);

        this.supplierService.seedSuppliers(dtos);
    }
}
