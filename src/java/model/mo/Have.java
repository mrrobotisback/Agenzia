package model.mo;

public class Have {
    private Long userId;
    private Long travelCode ;
    private Long quantity;
    private Have[] have;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Have[] getHave() {
        return have;
    }

    public void setHave(Have[] have) {
        this.have = have;
    }
}
