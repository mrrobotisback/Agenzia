package model.mo;

public class Details {
    private Long orderNumber;
    private Long travelCode ;
    private Long quantity;
    private Details[] details;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getTravelCode() {
        return travelCode;
    }

    public void setTravelCode(Long travelCode) {
        this.travelCode = travelCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Details[] getDetails() {
        return details;
    }

    public void setDetails(Details[] details) {
        this.details = details;
    }
}
