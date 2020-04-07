package car_dealer.models.dtos.viewDtos;

import com.google.gson.annotations.Expose;



public class CarToyotaDto {

    @Expose
    private long Id;
    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private long TravelledDistance;

    public CarToyotaDto() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        TravelledDistance = travelledDistance;
    }


}
