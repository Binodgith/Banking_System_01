package App;

import DAOClasses.BankPanel;
import Styles.ConsoleColors;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank_Dashboard {
    public static void main(String[] args) {
        BankDash();
    }


    protected static void BankDash(){
        Scanner sc= new Scanner(System.in);
        User_Dashboard user= new User_Dashboard();
        BankPanel userPanel= new BankPanel();

        long TEMP_ACCOUNT=0;
        String TEMP_USERNAME=null;
        Pattern pattern;
        Matcher matcher;


        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.BLUE_BACKGROUND+"                                  STRAIGHT BANK OF INDIA                             "+ConsoleColors.RESET);

        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);
        System.out.println("       "+ConsoleColors.BLACK_BOLD+ConsoleColors.PURPLE_BACKGROUND+"                     Welcome to Bank Dashboard                        "+ConsoleColors.RESET);

        System.out.println();
        System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Staff Login"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);


        System.out.println();
        System.out.println(ConsoleColors.PURPLE+"        ==================================================================="+ConsoleColors.RESET);


        while (true){
            String option= sc.nextLine();

        }
    }
}
