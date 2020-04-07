package productsShop.models.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserSoldWithAgeDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private ProductsDtoCount soldProducts;

    public UserSoldWithAgeDto() {
    }

    public UserSoldWithAgeDto(String firstName, String lastName, Integer age, ProductsDtoCount soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductsDtoCount getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsDtoCount soldProducts) {
        this.soldProducts = soldProducts;
    }
}
