import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<ATM> atms;

    public Bank(String name) {
        this.name = name;
        this.atms = new ArrayList<>();
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public int getTotalMoneyInNetwork() {
        return atms.stream().mapToInt(ATM::getTotalMoney).sum();
    }

    public void displayBankInfo() {
        System.out.println("Банк: " + name);
        System.out.println("Количество банкоматов: " + atms.size());
        for (int i = 0; i < atms.size(); i++) {
            System.out.println("Банкомат " + (i + 1) + ":");
            atms.get(i).displayATMInfo();
        }
        System.out.println("Общая сумма в сети банкоматов: " + getTotalMoneyInNetwork() + " грн");
    }
}