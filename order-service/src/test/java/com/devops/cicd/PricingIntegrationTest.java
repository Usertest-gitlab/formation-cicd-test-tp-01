package com.devops.cicd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile_vip_freeShipppingCost() {
        // Charger la configuration réelle
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Instancier le service avec la config
        PricingService service = new PricingService(config);

        // Scénario complet
        double montantHT = 100;
        boolean vip = true;

        double prixTTC = service.applyVat(montantHT);           // 100 + 20% = 120
        double prixVIP = service.applyVipDiscount(prixTTC, vip); // 120 * 0.9 = 108
        double total = prixVIP + service.shippingCost(prixVIP);  // 108 + 0 (livraison gratuite)

        // Vérifier le résultat final
        assertEquals(108.0, total);
    }
    
    @Test
    void fullPricingFlow_withRealConfigFile_notVip_freeShipppingCost() {
        // Charger la configuration réelle
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Instancier le service avec la config
        PricingService service = new PricingService(config);

        // Scénario complet
        double montantHT = 100;
        boolean vip = false;

        double prixTTC = service.applyVat(montantHT);           // 100 + 20% = 120
        double prixVIP = service.applyVipDiscount(prixTTC, vip); // 120 * 1 = 120
        double total = prixVIP + service.shippingCost(prixVIP);  // 120 + 0 (livraison gratuite)

        // Vérifier le résultat final
        assertEquals(120.0, total);
    }

    @Test
    void fullPricingFlow_withRealConfigFile_vip_ShipppingCost() {
        // Charger la configuration réelle
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Instancier le service avec la config
        PricingService service = new PricingService(config);

        // Scénario complet
        double montantHT = 10;
        boolean vip = true;

        double prixTTC = service.applyVat(montantHT);           // 10 + 20% = 12
        double prixVIP = service.applyVipDiscount(prixTTC, vip); // 12 * 0.9 = 10.8
        double total = prixVIP + service.shippingCost(prixVIP);  // 10.8 + 4.99

        // Vérifier le résultat final
        assertEquals(15.79, total, 0.001);
    }
    
    @Test
    void fullPricingFlow_withRealConfigFile_notVip_ShipppingCost() {
        // Charger la configuration réelle
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Instancier le service avec la config
        PricingService service = new PricingService(config);

        // Scénario complet
        double montantHT = 10;
        boolean vip = false;

        double prixTTC = service.applyVat(montantHT);           // 10 + 20% = 12
        double prixVIP = service.applyVipDiscount(prixTTC, vip); // 12 * 1 = 12
        double total = prixVIP + service.shippingCost(prixVIP);  // 12 + 4.99

        // Vérifier le résultat final
        assertEquals(16.99, total, 0.001);
    }
}