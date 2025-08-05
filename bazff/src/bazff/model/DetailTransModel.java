package bazff.model;

public class DetailTransModel {
    private int productSizeId;
    private int quantity;
    private int hargaSatuan;

    public DetailTransModel(int productSizeId, int quantity, int hargaSatuan) {
        this.productSizeId = productSizeId;
        this.quantity = quantity;
        this.hargaSatuan = hargaSatuan;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }
}
