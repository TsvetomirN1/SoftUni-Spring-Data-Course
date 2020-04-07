package car_dealer.services;

import org.springframework.stereotype.Service;

@Service
public interface SaleService {

    void createSales();
    int getRandomDiscounts();

}
