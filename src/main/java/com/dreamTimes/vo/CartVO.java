package com.dreamTimes.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {
    private List<CartProductVO> cartProductVoList;
    private boolean allChecked;
    private BigDecimal cartTotalPrice;

    public List<CartProductVO> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVO> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }
}
