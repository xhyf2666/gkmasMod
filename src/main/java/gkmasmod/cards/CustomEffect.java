package gkmasmod.cards;

public class CustomEffect{
    String type="";
    int price=50;
    String description="";
    int amount=0;

    public CustomEffect(String type,int amount, int price, String description){
        this.type=type;
        this.amount=amount;
        this.price=price;
        this.description=description;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
