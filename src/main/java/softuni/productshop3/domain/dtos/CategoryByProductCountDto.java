package softuni.productshop3.domain.dtos;

import com.google.gson.annotations.Expose;

public class CategoryByProductCountDto {
    @Expose
    private String name;
    @Expose
    private int productCount;
    @Expose
    private double averagePrice;
    @Expose
    private double totalRevenue;

    public CategoryByProductCountDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
