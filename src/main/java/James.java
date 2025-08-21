import java.util.Scanner;
public class James {
    public static void displayList(String[] arr) {
        int count = 1;
        for (String str: arr) {
            if (str != null) {
                System.out.println("<" + count + "> " + str);
            }
            count++;
        }
    }

    public static void main(String[] args) {
        String[] store = new String[100];
        int size = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Hey There! James at your service. \n" +
                "How can I help you today?");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            System.out.println("input: ");
            String query = input.nextLine().trim();
            System.out.println("--------------------------------------------------------------");
            if (query.equalsIgnoreCase("bye")) break;
            if (query.equalsIgnoreCase("list")) {
                System.out.print("output:\n");
                System.out.println("Task count: " + size);
                James.displayList(store);
            } else {
                System.out.println("output:\n" + "added: " + query);
                store[size] = query;
                size++;
            }
            System.out.println("--------------------------------------------------------------");
        }
        System.out.println("Bye, feel free to ask me anything anytime!");
    }
}



