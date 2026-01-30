package org.example;
import java.util.Scanner;


public class Main {

    static UserRepository userRepository = new UserRepository();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        User u = Main.belepes();
        try {
            menu(u);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
        public static User belepes() {

            String nev = null;
            String pass = null;
            int valasz;

            User logined = null;


            while (logined == null) {
                System.out.println("1. Regisztrálni szeretnék! 2. Bejelentekzni szeretnék");
                valasz = scanner.nextInt();
                scanner.nextLine();
                switch (valasz) {

                    //Regisztralas
                    case 1:
                        logined = registration(nev, logined);
                        break;
                    //Authentication
                    case 2:
                        logined = auhentication(nev,pass,logined);
                    break;

                    default:
                        System.out.println("nincs ilyen opció");
                        break;
                }
            }

            return logined;
        }

        public static void menu(User logined) throws IllegalAccessException {
            if (logined == null) throw new IllegalAccessException("logined is null");

            boolean exit = false;
            String fiok = null;


            while (!exit){
                int amount = 0;
                int balance = 0;
                System.out.println("válasszon egy menüpontott:");
                System.out.println("1. utalás 2. pénz levétele 3. pénz feltöltése 4. kilépés 5. kijelnetkezés 6. consol");
                int valasz = scanner.nextInt();
                switch (valasz){
                    case 1:
                        transfer(fiok, amount, balance, logined);
                        break;
                    case 2:
                        whitdraw(amount, balance, logined);
                        break;
                    case 3:
                      deposit(amount, balance, logined);
                        break;
                    case 4:
                        exit(exit);
                        break;
                    case 5:
                        logined = log_out(logined);
                        logined = belepes();
                        break;
                    case 6:
                      oppem_console(logined);
                        break;
                    default:
                        System.out.println("nincs ilyen opcioó");
                }
            }
        }

    public static void console(){
        boolean exit = false;
        Main m = new Main();
        String felhasznalo = null;
        int ertek = 0;
        User target = null;
        scanner.nextLine();
        while (!exit) {
            System.out.println("Válaszd ki, ogy mit szeretnél");
            System.out.println("1. pénz hozzáadása 2. felhasználók listája 3. pénz levonása 4. profil törlése 5. Jogosultság kezelés 6. kilépés");
            int valasz = scanner.nextInt();
            scanner.nextLine();
            switch (valasz) {
                case 1:
                    add_money(felhasznalo, ertek, target);
                    break;
                case 2:
                    profiles();
                    break;
                case 3:
                    deduct_money(felhasznalo, ertek, target);
                    break;
                case 4:
                    delete(felhasznalo, target);
                    break;
                case 5:
                    permission();
                    break;
                case 6:
                    System.out.println("Sikeresen kilépet a consolbol");
                    exit = true;
                    break;
                default:
                    System.out.println("nincs ilyen opció");
                    break;
            }
        }
    }

    public static User registration(String nev, User logined){
        System.out.println("Adja meg a nevét");
        nev = scanner.nextLine();
        System.out.println("Adja meg a jelszavát");
        String passw1 = scanner.nextLine();
        System.out.println("erősítse meg a jelszavát");
        String passw2 = scanner.nextLine();

        if (passw1.equals(passw2)) {
            try {
                logined = userRepository.register(nev, passw1);
            } catch (AlreadyRegisteredUserException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("A jelszónak meg kell egyeznie");
        }
        return logined;
    }

    public static User auhentication(String nev, String pass, User logined){
        System.out.println("Add meg a neved");
        nev = scanner.nextLine();
        System.out.println("Add meg a jelszavad");
        pass = scanner.nextLine();

        try {
            logined = userRepository.login(nev, pass);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        return logined;
    }

    public static void transfer(String fiok, int amount, int balance, User logined){
        User target = null;

        do {
            System.out.println("Add meg a nevét akinek utali akarsz");
            fiok = scanner.nextLine();
            target = userRepository.getUser(fiok);

        } while (target == null);

        System.out.println("mennyi pénzt akarsz utalni?");
        amount = scanner.nextInt();
        scanner.nextLine();

        try {
            balance = logined.withdraw(amount);
            System.out.println(logined.getUsername() + " Hozzáadva az egyenlegedhez " + balance + "Ft-t");
            balance = target.deposit(amount);
            System.out.println(target.getUsername() + " Uj egzenleg " + balance + "Ft-t");
        } catch (InsufficientException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void whitdraw(int amount, int balance, User logined){
        System.out.println("mennyi pénzt akarsz levenni?");
        amount = scanner.nextInt();
        scanner.nextLine();

        try {
            balance = logined.withdraw(amount);
            System.out.println("levontunk az egyenlegedből " +amount + "Ft-t");
        } catch (InsufficientException e) {
            System.out.println("nincs fedezet");
        }
    }

    public static void deposit(int amount, int balance, User logined){
        System.out.println("mennyi pénzt akarsz feltölteni?");
        amount = scanner.nextInt();
        scanner.nextLine();
        balance= logined.deposit(amount);
        System.out.println(logined.getUsername() + "az uj egyenleged: " + balance);
    }

    public static void exit(boolean exit){
        System.out.println("Sikeresen kilétél");
        System.out.println("viszlát!");
        exit = true;
    }

    public static User log_out(User logined){
        System.out.println("sikeresen kijelenkeztél");
        return null;
    }

    public  static void oppem_console(User logined){
        if (logined != null && logined.getAdmin()){
            System.out.println("Sikeresen beléptél a Consolba");
            console();
        }else {
            System.out.println("nincs hozzáférésed " + logined.getUsername());
            boolean testadmin = logined.getAdmin();
            System.out.println(logined);
            System.out.println(testadmin);
        }
    }

    public static void add_money(String felhasznalo, int ertek, User target){
        System.out.println("melyik felhasználonak akarsz pénzt adni?");
        felhasznalo = scanner.nextLine();
        System.out.println("mennyi pénzt adjunk " + felhasznalo + " számlájára?");
        ertek = scanner.nextInt();
        scanner.nextLine();
        target = UserRepository.profil.get(felhasznalo);
        try {
            target.withdraw(ertek);
            System.out.println("az új egyenlege " + target + "-nek=-nak" + target.getMoney());
        }catch (InsufficientException e){
            System.out.println("nincs ilyen felhasználó");
        }
    }

    public static void profiles(){
        for (User u : UserRepository.profil.values()){
            System.out.println("Név: " + u.getUsername() +
                    " | Egyenleg: " + u.getMoney() + " Ft" +
                    " | Admin: " + (u.getAdmin() ? "Igen" : "Nem"));
        }
    }

    public static void deduct_money(String felhasznalo, int ertek, User target){
        System.out.println("melyik felhasználótol akarsz pénzt levonni?");
        felhasznalo = scanner.nextLine();
        System.out.println("mennyi pénzt vonjunk le " + felhasznalo + " számlájáról?");
        ertek = scanner.nextInt();
        scanner.nextLine();
        target = UserRepository.profil.get(felhasznalo);
        try {
            target.withdraw(ertek);
            System.out.println("az új egyenlege " + target + "-nek=-nak" + target.getMoney());
        }catch (InsufficientException e){
            System.out.println("nincs ilyen felhasználó");
        }
    }

    public static void delete(String felhasznalo, User target){
        System.out.println("melyik felhasználót akarod tötölni?");
        felhasznalo = scanner.nextLine();
        if (felhasznalo != null){
            target = UserRepository.profil.get(felhasznalo);
            UserRepository.profil.remove(target);
            System.out.println("Sikeresen törölve: " + felhasznalo);
        }else {
            System.out.println("sikeresen törölted" + felhasznalo + "-ot/-et");
            System.out.println("nincs ilyen felhasználó");
        }
    }

    public static void permission(){
        System.out.println("melyik felhasználó jogusultságát akarod kezelni?");
        String felhazsnalo3 = scanner.nextLine();
        User target3 = UserRepository.profil.get(felhazsnalo3);
        target3.jogosultsag();
    }
}