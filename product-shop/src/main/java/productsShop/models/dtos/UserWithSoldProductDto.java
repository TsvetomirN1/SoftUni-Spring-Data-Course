package productsShop.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserWithSoldProductDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<SoldProductsDto> soldProducts;

    public UserWithSoldProductDto() {
    }

    public UserWithSoldProductDto(String firstName, String lastName, List<SoldProductsDto> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SoldProductsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductsDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
