import java.util.Scanner;
public class James {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Hey There! James at your service. \n" +
                "How can I help you today?");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            System.out.println("input: ");
            String query = input.nextLine().trim();
            System.out.println("--------------------------------------------------------------");
            if (query.equalsIgnoreCase("bye")) break;
            System.out.println("output: " + query);
            System.out.println("--------------------------------------------------------------");
        }
        System.out.println("Bye, feel free to ask me anything anytime!");
    }
}



