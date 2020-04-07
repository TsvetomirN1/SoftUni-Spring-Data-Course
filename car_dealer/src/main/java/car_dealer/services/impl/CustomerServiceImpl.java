package car_dealer.services.impl;

import car_dealer.models.dtos.seedDtos.CustomerSeedDto;
import car_dealer.models.dtos.viewDtos.CustomerOrderedDto;
import car_dealer.models.dtos.viewDtos.CustomerSalesDto;
import car_dealer.models.entities.Customer;
import car_dealer.models.entities.Part;
import car_dealer.models.entities.Sale;
import car_dealer.repositories.CustomerRepository;
import car_dealer.services.CustomerService;
import car_dealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper,
                               ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {

        this.modelMapper.addMappings(new PropertyMap<CustomerSeedDto, Customer>() {

            @Override
            protected void configure() {
                boolean isYoungDriver = false;

                if (source.isYoungDriver()) {
                    isYoungDriver = true;
                }
                map().setYoungDriver(isYoungDriver);
            }
        });

        if (this.customerRepository.count() != 0) {
            return;
        }

        Arrays.stream(customerSeedDtos)
                .forEach(customerSeedDto -> {
                    if (this.validationUtil.isValid(customerSeedDto)) {
                        Customer customer = this.modelMapper
                                .map(customerSeedDto, Customer.class);

                        customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getBirthDate(),
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                        this.customerRepository.saveAndFlush(customer);

                    } else {
                        this.validationUtil.getViolations(customerSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });

    }

    @Override
    public Customer getRandomCustomer() {

        Random random = new Random();
        long index = random.nextInt((int) this.customerRepository.count()) + 1;

        return this.customerRepository.getOne(index);

    }

    @Override
    public List<CustomerOrderedDto> getAllCustomersOrderByBirthDateAscAndIsYoungDriver() {

        return this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerSalesDto> getAllCustomersWithSales() {

        List<CustomerSalesDto> dtos = this.customerRepository
                .getAllCustomersWithSales()
                .stream().map(c -> {
                    CustomerSalesDto cs = new CustomerSalesDto();
                    cs.setFullName(c.getName());
                    cs.setBoughtCars(c.getSales().size());

                    BigDecimal total = BigDecimal.ZERO;

                    for (Sale s : c.getSales()) {
                        BigDecimal totalParts = BigDecimal.ZERO;

                        for (Part p : s.getCar().getParts()) {
                            totalParts = totalParts.add(p.getPrice());
                        }
                        double discount = s.getDiscount() / 100.0;

                        totalParts = totalParts.subtract(totalParts.multiply(new BigDecimal(discount))
                                , MathContext.DECIMAL32);

                        BigDecimal totalForCar = totalParts;

                        total = total.add(totalForCar);
                    }

                    //.setScale(2, RoundingMode.HALF_DOWN) -> to round the decimal to 2 digits after floating point
                    cs.setSpentMoney(total.setScale(2, RoundingMode.HALF_DOWN));
                    return cs;
                })
                .collect(Collectors.toList());

        return dtos.stream()
                .sorted((a, b) -> {
                    int val = b.getSpentMoney().compareTo(a.getSpentMoney());

                    if (val == 0) {
                        val = Integer.compare(b.getBoughtCars(), a.getBoughtCars());
                    }

                    return val;
                })
                .collect(Collectors.toList());
    }
}


