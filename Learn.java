
class BankAccount{
    private String FirstName;
    private String LastName;
    private int AccountNumber;
    private int Balance;

    public BankAccount(String firstName, String lastName, int accountNumber, int balance) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.AccountNumber = accountNumber;
        this.Balance = balance;
    }
    //setters
    public void SetFirstName(String firstName){
        this.FirstName = firstName;
    }
    public void SetLastname(String lastName){
        this.LastName = lastName;
    }
    public int Deposit(int amount){
        if(amount>0){
            Balance += amount;
        }
        return Balance;
    }
    public int Withdraw(int amount){
        if(amount>0 && amount<Balance){
            Balance -= amount;
        }
        return Balance;
    }

    public int getBalance() {
        System.out.println("Current balance: " + Balance);
        return  Balance;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }
}
public class Learn {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("John", "Doe", 12345, 1000);
        account1.getBalance();
        account1.Deposit(500);
        account1.getBalance();
        account1.Withdraw(200);
        account1.getBalance();
    }
}