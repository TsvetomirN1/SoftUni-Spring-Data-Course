package car_dealer.models.dtos.viewDtos;


import car_dealer.models.entities.Sale;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CustomerOrderedDto {
    @Expose
    private long Id;
    @Expose
    private String Name;
    @Expose
    private String BirthDate;
    @Expose
    private boolean IsYoungDriver;
    @Expose
    private List<Sale> Sales;


    public CustomerOrderedDto() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return IsYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        IsYoungDriver = youngDriver;
    }

    public List<Sale> getSales() {
        return Sales;
    }

    public void setSales(List<Sale> sales) {
        Sales = sales;
    }
}
