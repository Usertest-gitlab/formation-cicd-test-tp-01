package com.devops.cicd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PricingServiceTest {

    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);
    
    @Test
    void applyVatTest() { 
        // Tests TVA
        assertEquals(service.applyVat(10.0), 12.0);
    }

    @Test
    void applyVipDiscountTest() { 
        // Tests remise VIP
        assertEquals(service.applyVipDiscount(12.0, false), 12.0);
        assertEquals(service.applyVipDiscount(12.0, true), 10.8);
    }
    
    @Test
    void shippingCostTest() { 
        // Tests frais de livraison
        assertEquals(service.shippingCost(10.0), 4.99);
        assertEquals(service.shippingCost(50.0), 4.99);
        assertEquals(service.shippingCost(60.0), 0.00);
    }
}
