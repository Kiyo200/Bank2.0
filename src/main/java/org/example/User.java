package org.example;

public class User {
    private String username;
    private String password;
    private int money = 1000000;
    private boolean admin;

    public User(String username, String password, int money, boolean admin){
        this.username = username;
        this.password = password;
        this.money = money;
        this.admin = admin;
    }
    public int deposit(int amount){
        money += amount;

        return money;
    }



    public int withdraw(int amount) throws InsufficientException {
        //BigDecimal
        if(money - amount >= 0) {
            money -= amount;
            return  money;
        }else {
            throw new InsufficientException("nincs fedezete a tranyakcionak");
        }
    }

    public boolean jogosultsag(){
        this.admin = !admin;
        System.out.println("sikeresen megváltoztattad a jogosultságát");
        return this.admin;
    }

    public  String getPassword() {return password;}
    public int getMoney() {return money;}
    public boolean getAdmin() {return admin;}
    public String getUsername() {return username;}



}
