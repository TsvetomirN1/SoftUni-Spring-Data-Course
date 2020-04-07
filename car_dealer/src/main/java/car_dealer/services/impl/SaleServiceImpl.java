package car_dealer.services.impl;

import car_dealer.models.entities.Sale;
import car_dealer.repositories.SaleRepository;
import car_dealer.services.CarService;
import car_dealer.services.CustomerService;
import car_dealer.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class SaleServiceImpl implements SaleService {

    private final CarService carService;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final SaleRepository saleRepository;


    @Autowired
    public SaleServiceImpl(CarService carService, ModelMapper modelMapper,
                           CustomerService customerService, SaleRepository saleRepository) {
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.saleRepository = saleRepository;
    }

    @Override
    public void createSales() {

        for (long i = 0; i < 50; i++) {
            Sale sale = new Sale();
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.getRandomCustomer());
            sale.setDiscount(getRandomDiscounts());

            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public int getRandomDiscounts() {

        List<Integer> discounts = new ArrayList<>();
        discounts.add(0);
        discounts.add(5);
        discounts.add(10);
        discounts.add(15);
        discounts.add(20);
        discounts.add(30);
        discounts.add(40);
        discounts.add(50);
        Random random = new Random();
        int index = random.nextInt(discounts.size());
        return discounts.get(index);
    }
}
