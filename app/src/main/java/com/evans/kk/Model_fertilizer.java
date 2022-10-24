package com.evans.kk;
public class Model_fertilizer {
    private String fertilizerImage,fertilizerName,fertilizerQuantity,fertilizerPrice,fertilizerId,quantityOrdered;

    public Model_fertilizer() {
    }

    public Model_fertilizer(String fertilizerImage, String fertilizerName, String fertilizerQuantity, String fertilizerPrice, String fertilizerId, String quantityOrdered) {
        this.fertilizerImage = fertilizerImage;
        this.fertilizerName = fertilizerName;
        this.fertilizerQuantity = fertilizerQuantity;
        this.fertilizerPrice = fertilizerPrice;
        this.fertilizerId = fertilizerId;
        this.quantityOrdered = quantityOrdered;
    }

    public String getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(String quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(String fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    public String getFertilizerImage() {
        return fertilizerImage;
    }

    public void setFertilizerImage(String fertilizerImage) {
        this.fertilizerImage = fertilizerImage;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public String getFertilizerQuantity() {
        return fertilizerQuantity;
    }

    public void setFertilizerQuantity(String fertilizerQuantity) {
        this.fertilizerQuantity = fertilizerQuantity;
    }

    public String getFertilizerPrice() {
        return fertilizerPrice;
    }

    public void setFertilizerPrice(String fertilizerPrice) {
        this.fertilizerPrice = fertilizerPrice;
    }
}
