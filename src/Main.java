import controller.BankController;
import model.Bank;
import view.ConsoleView;

/**
 * The entry point of the Banking System application.
 */
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ConsoleView view = new ConsoleView();
        BankController controller = new BankController(bank, view);
        
        controller.start();
    }
}
