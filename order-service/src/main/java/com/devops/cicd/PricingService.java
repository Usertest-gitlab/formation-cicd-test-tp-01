public final class PricingService {

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        return
    }

    public double applyVipDiscount(double amount, boolean vip) {
        //TODO
    }

    public double shippingCost(double amount) {
        //TODO
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        //TODO
    }
}
