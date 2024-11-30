import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("ПримерБанк");

        // Создаем банкомат
        ATM atm1 = new ATM(50, 30);
        atm1.initializeATM(Map.of(
                500, 10,
                200, 20,
                100, 30,
                50, 50
        ));

        // Создаем второй банкомат
        ATM atm2 = new ATM(20, 50);
        atm2.initializeATM(Map.of(
                100, 10,
                50, 20,
                20, 30
        ));

        bank.addATM(atm1);
        bank.addATM(atm2);

        // Вывод информации о банке и банкоматах
        bank.displayBankInfo();

        // Снятие денег
        try {
            Map<Integer, Integer> withdrawal = atm1.withdrawMoney(870);
            System.out.println("Выдано:");
            withdrawal.forEach((denomination, count) ->
                    System.out.println(denomination + " грн: " + count + " банкнот"));
        } catch (ATMException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Вывод обновленной информации
        bank.displayBankInfo();
    }
}