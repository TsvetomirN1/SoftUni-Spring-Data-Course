package car_dealer.models.dtos.viewDtos;

import com.google.gson.annotations.Expose;

public class LocalSuppliersDto {

    @Expose
    private long Id;
    @Expose
    private String Name;
    @Expose
    private int partsCount;

    public LocalSuppliersDto() {
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

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
