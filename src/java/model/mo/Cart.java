package model.mo;

public class Cart {
    private Long userId;
    private Double total;
    private Cart[] cart;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cart[] getCart() {
        return cart;
    }

    public void setCart(Cart[] cart) {
        this.cart = cart;
    }

}
