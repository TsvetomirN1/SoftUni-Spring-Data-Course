package productsShop.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ProductsDtoCount {

    @Expose
    private Long count;
    @Expose
    private List<ProductNameAndPriceDto> products;

    public ProductsDtoCount() {
    }

    public ProductsDtoCount(Long count, List<ProductNameAndPriceDto> products) {
        this.count = count;
        this.products = products;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<ProductNameAndPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNameAndPriceDto> products) {
        this.products = products;
    }
}
