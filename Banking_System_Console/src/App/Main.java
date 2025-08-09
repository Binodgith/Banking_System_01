package App;

import java.io.IOException;
import java.util.Scanner;

import Models.UserAccount;
import Styles.ConsoleColors;

public class Main {

    public static void main(String[] args) throws IOException{
        HomeDash();
    }



    static void HomeDash() throws IOException {
       Scanner sc= new Scanner(System.in);
       Bank_Dashboard bank= new Bank_Dashboard();
        User_Dashboard user= new User_Dashboard();

        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.BLUE_BACKGROUND+"                                  STRAIGHT BANK OF INDIA                             "+ConsoleColors.RESET);

        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.BLUE_BRIGHT+"||"+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" User Dashboard"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BRIGHT+"||"+ConsoleColors.RESET+"  Press 2 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Bank Dashboard"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.BLUE_BRIGHT+"||");
        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);


        while(true){
            System.out.println(" Enter any Option.");
            String option= sc.nextLine();
            if (option.equals("1")){
                user.UserDash();
                break;
            }
            else if (option.equals("2")) {
                bank.BankDash();
                break;

            }
            else{
                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+" Invalid Input ! Enter 1 or 2 "+ConsoleColors.RESET);
            }
        }



    }
}
