package com.devops.cicd;

public final class PricingService {

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        return amountExclVat * (1 + config.getVatRate() / 100.0); // prix avec TVA
    }

    public double applyVipDiscount(double amount, boolean vip) {
        return vip ? amount * 0.9 : amount; // prix avec remise de 10% si VIP
    }

    public double shippingCost(double amount) {
        return amount > config.getFreeShippingThreshold() ? 0.0 : 4.99;
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double prix_ttc = applyVat(amountExclVat);
        double prix_remise = applyVipDiscount(prix_ttc, vip);
        double prix_final = prix_remise + shippingCost(prix_remise);
        return prix_final;
    }
}
