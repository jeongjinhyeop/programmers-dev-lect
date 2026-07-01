package org.example.springtheory.ch06.aopEx;

public class ProductServiceImpl implements ProductService {
    @Override
    public String getProduct(String code) {
        try {
            Thread.sleep(30); // 30ms 지연
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return "상품: " + code;
    }
}

