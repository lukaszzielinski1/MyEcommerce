package lukaszzielinski.productcatalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final String uuid;
    private final String name;
    private final String desc;
    private BigDecimal price;
    private String image;
    private boolean online;

    public Product(UUID uuid, String name, String desc) {
        this.uuid = uuid.toString();
        this.name = name;
        this.desc = desc;
    }

    public String getId() {
        return uuid;
    }

    public UUID getUUID() {
        return UUID.fromString(uuid);
    }
    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void changePrice(BigDecimal newValue) {
        price = newValue;
    }
    public void setImageKey(String imageKey) {
        this.image = imageKey;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public boolean getOnline() {
        return online;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }


}