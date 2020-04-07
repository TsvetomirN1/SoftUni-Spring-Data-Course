package productsShop.models.dtos;

import com.google.gson.annotations.Expose;

public class CategoriesByCountDto {

    @Expose
    private String category;
    @Expose
    private Integer productsCount;
    @Expose
    private Double averagePrice;
    @Expose
    private Double totalRevenue;

    public CategoriesByCountDto() {
    }

    public CategoriesByCountDto(String category, Integer productsCount, Double averagePrice, Double totalRevenue) {
        this.category = category;
        this.productsCount = productsCount;
        this.averagePrice = (double)Math.round(averagePrice * 1000000)/1000000;
        this.totalRevenue =  (double)Math.round(totalRevenue * 100)/100;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
