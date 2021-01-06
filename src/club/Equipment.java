package club;

public class Equipment {
    private final String name;
    private int quantity;

    public Equipment(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean loan(int number) {
        if (quantity >= number) {
            quantity -= number;
            return true;
        }
        System.out.println("Failed! Only " + quantity + " " + name + " left!");
        return false;
    }

    public void returnRent(int number) {
        quantity+=number;
    }
    @Override
    public String toString(){
        return "Have " + quantity+" "+name;
    }

    public String[] toData() {
        return new String[]{name, String.valueOf(quantity)};
    }
}
