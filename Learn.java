
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








}
