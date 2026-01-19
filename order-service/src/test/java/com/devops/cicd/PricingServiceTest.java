class PricingServiceTest {

    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);
    
    // Tests TVA
    assertEquals(service.applyVat(10.0), 12.0);

    // Tests remise VIP
    assertEquals(service.applyVipDiscount(12.0, false), 12.0);
    assertEquals(service.applyVipDiscount(10.0, true), 10.8);

    // Tests frais de livraison
    assertEquals(service.shippingCost(10.0), 4.99);
    assertEquals(service.shippingCost(50.0), 4.99);
    assertEquals(service.shippingCost(60.0), 0.00);

    // Tests calcul total
    assertEquals(service.finalTotal(30.0, false), 40.99);
    assertEquals(service.finalTotal(30.0, true), 37.39);
    assertEquals(service.finalTotal(65.0, false), 78.0);
    assertEquals(service.finalTotal(65.0, true), 70.2);
}
