package car_dealer.models.dtos.viewDtos;

import car_dealer.models.dtos.seedDtos.CarSeedDto;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CarPartsDto {

    @Expose
    private CarSeedDto car;
    @Expose
    private List<PartDto> parts;

    public CarPartsDto() {
    }

    public CarSeedDto getCar() {
        return car;
    }

    public void setCar(CarSeedDto car) {
        this.car = car;
    }

    public List<PartDto> getParts() {
        return parts;
    }

    public void setParts(List<PartDto> parts) {
        this.parts = parts;
    }
}
