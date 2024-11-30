import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<Integer, Integer> banknotes = new HashMap<>();
    private int minWithdrawal;
    private int maxBanknotes;

    public ATM(int minWithdrawal, int maxBanknotes) {
        this.minWithdrawal = minWithdrawal;
        this.maxBanknotes = maxBanknotes;

        // Инициализация номиналов банкнот
        int[] denominations = {1, 2, 5, 10, 20, 50, 100, 200, 500};
        for (int denomination : denominations) {
            banknotes.put(denomination, 0);
        }
    }

    public void initializeATM(Map<Integer, Integer> initialBanknotes) {
        initialBanknotes.forEach((denomination, count) ->
                banknotes.put(denomination, banknotes.getOrDefault(denomination, 0) + count));
    }

    public void depositMoney(Map<Integer, Integer> depositBanknotes) {
        depositBanknotes.forEach((denomination, count) ->
                banknotes.put(denomination, banknotes.getOrDefault(denomination, 0) + count));
    }

    public Map<Integer, Integer> withdrawMoney(int amount) throws ATMException {
        if (amount < minWithdrawal) {
            throw new InvalidAmountException("Сумма меньше минимальной для выдачи: " + minWithdrawal);
        }

        Map<Integer, Integer> withdrawal = new HashMap<>();
        int totalBanknotes = 0;
        int remainingAmount = amount;

        for (int denomination : banknotes.keySet().stream().sorted((a, b) -> b - a).toList()) {
            int count = Math.min(remainingAmount / denomination, banknotes.get(denomination));
            if (count > 0) {
                withdrawal.put(denomination, count);
                remainingAmount -= count * denomination;
                totalBanknotes += count;

                if (totalBanknotes > maxBanknotes) {
                    throw new ExceedsBanknoteLimitException("Превышено максимальное количество банкнот для выдачи.");
                }
            }
        }

        if (remainingAmount > 0) {
            throw new InsufficientFundsException("Невозможно выдать запрошенную сумму.");
        }

        // Обновляем баланс банкомата
        withdrawal.forEach((denomination, count) ->
                banknotes.put(denomination, banknotes.get(denomination) - count));

        return withdrawal;
    }

    public int getTotalMoney() {
        return banknotes.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
    }

    public void displayATMInfo() {
        System.out.println("Банкомат:");
        banknotes.forEach((denomination, count) ->
                System.out.println(denomination + " грн: " + count + " банкнот"));
        System.out.println("Итого: " + getTotalMoney() + " грн");
    }
}