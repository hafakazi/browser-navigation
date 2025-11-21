import java.util.EmptyStackException;
import java.util.Scanner;

/********************************************************
* Driver program to test the browser navigation system. *
*********************************************************/

public class Main {
    public static void main(String[] args) {
        BrowserNavigation nav = new BrowserNavigation();
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("1. visit <url> | 2. back | 3. forward | 4. history | 5. clear | 6. save | 7. restore | 8. exit");

            while (true) {
                System.out.print("> ");
                if (!sc.hasNext()) 
                break; // EOF safety

                String cmd = sc.next();

                switch (cmd) {
                    case "1":
                    case "visit": {
                        if (sc.hasNext()) {
                            String url = sc.next();
                            System.out.println(nav.visitWebsite(url));
                        } else {
                            System.out.println("Usage: visit <url>");
                        }
                        break;
                    }
                    case "2":
                    case "back": {
                        try {
                            System.out.println(nav.goBack());
                        } catch (EmptyStackException e) {
                            System.out.println("No previous page available.");
                        }
                        break;
                    }
                    case "3":
                    case "forward": {
                        try {
                            System.out.println(nav.goForward());
                        } catch (EmptyStackException e) {
                            System.out.println("No forward page available.");
                        }
                        break;
                    }
                    case "4":
                    case "history": {
                        System.out.print(nav.showHistory());
                        break;
                    }
                    case "5":
                    case "clear": {
                        System.out.println(nav.clearHistory());
                        break;
                    }
                    case "6":
                    case "save": {
                        System.out.println(nav.closeBrowser());
                        break;
                    }
                    case "7":
                    case "restore": {
                        System.out.println(nav.restoreLastSession());
                        break;
                    }
                    case "8":
                    case "exit": {
                        System.out.println("Exiting.");
                        break;
                    }
                    default: {
                        System.out.println("Unknown command.");
                    }
                }

                if ("exit".equals(cmd) || "8".equals(cmd)) 
                break;
            }
        } finally {
            sc.close();
        }
    }
}
