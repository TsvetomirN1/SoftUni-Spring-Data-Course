package car_dealer.models.entities;


import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    private Car car;
    private Customer customer;
    private int discount;

    public Sale() {
    }
    public Sale(Car car, Customer customer, int discount) {
        this.car = car;
        this.customer = customer;
        this.discount = discount;
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
