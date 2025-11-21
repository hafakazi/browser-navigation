import java.io.*;
import java.util.EmptyStackException;
import java.util.Iterator;

// Main functions: visits, back, forward, history, save, restore.

 public class BrowserNavigation {
    private final BrowserStack<String> backStack = new BrowserStack<>();
    private final BrowserStack<String> forwardStack = new BrowserStack<>();
    private final BrowserQueue<String> historyQueue = new BrowserQueue<>();
    private String currentPage = null;

    private static final String SAVE_FILE = "session_data.txt";

    // 1. Visit
    public String visitWebsite(String url) {
        if (url == null || url.isEmpty()) {
            return "Invalid URL.";
        }

        if (currentPage != null) {
            backStack.push(currentPage);
        }

        forwardStack.clear();       // new branch clears forward stack
        historyQueue.offer(url);    // record history (queue)
        currentPage = url;

        return "Now at [" + url + "]";
    }

    // 2. Back
    public String goBack() {
        if (currentPage == null || backStack.isEmpty()) {
            throw new EmptyStackException();
        }

        forwardStack.push(currentPage);
        currentPage = backStack.pop();

        return "Now at [" + currentPage + "]";
    }

    // 3. Forward
    public String goForward() {
        if (currentPage == null || forwardStack.isEmpty()) {
            throw new EmptyStackException();
        }

        backStack.push(currentPage);
        currentPage = forwardStack.pop();

        return "Now at [" + currentPage + "]";
    }

    // 4. Show history
    public String showHistory() {
        if (historyQueue.isEmpty()) {
            return "No browsing history available.";
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;

        for (String s : historyQueue) {
            sb.append(i++).append(". ").append(s).append(System.lineSeparator());
        }

        return sb.toString();
    }

    // 5. Clear history
    public String clearHistory() {
        historyQueue.clear();
        return "Browsing history cleared.";
    }

    // 6. Close browser, then save stacks/queue using Iterators
    public String closeBrowser() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            pw.println("CURRENT");
            pw.println(currentPage == null ? "" : currentPage);

            // back (saved top → bottom using StackIterator)
            pw.println("BACK " + backStack.size());
            Iterator<String> itB = new StackIterator<>(backStack);
            while (itB.hasNext()) {
                pw.println(itB.next());
            }

            // forward (saved top → bottom)
            pw.println("FORWARD " + forwardStack.size());
            Iterator<String> itF = new StackIterator<>(forwardStack);
            while (itF.hasNext()) {
                pw.println(itF.next());
            }

            // history (in chronological order)
            pw.println("HISTORY " + historyQueue.size());
            for (String s : historyQueue) {
                pw.println(s);
            }

        } catch (IOException e) {
            return "Failed to save session: " + e.getMessage();
        }

        return "Browser session saved.";
    }

    // 7. Restore last session from file
    public String restoreLastSession() {
        File f = new File(SAVE_FILE);
        if (!f.exists()) {
            return "No previous session found.";
        }

        backStack.clear();
        forwardStack.clear();
        historyQueue.clear();
        currentPage = null;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            // CURRENT
            String header = br.readLine();
            
            if (header == null || !"CURRENT".equals(header)) {
                throw new IOException("Bad file format");
            }

            String cp = br.readLine();
            
            if (cp != null && cp.length() > 0) {
                currentPage = cp;
            }

            // BACK
            String bHdr = br.readLine();
            
            if (bHdr == null || !bHdr.startsWith("BACK ")) {
                throw new IOException("Bad file format");
            }
            
            int bCount = Integer.parseInt(bHdr.substring(5).trim());
            String[] b = new String[bCount];
            
            for (int i = 0; i < bCount; i++) { 
                String s = br.readLine(); 
                
                if (s==null) {
                    throw new IOException("EOF");
                }

                b[i]=s; 
            }
            // saved top → bottom 
            
            // rebuild by pushing bottom → top so the original top comes out first
            for (int i = bCount-1; i >= 0; i--) {
                backStack.push(b[i]);
            }

            // FORWARD
            String fHdr = br.readLine();
            
            if (fHdr == null || !fHdr.startsWith("FORWARD ")) {
                throw new IOException("Bad file format");
            }

            int fCount = Integer.parseInt(fHdr.substring(8).trim());
            String[] fwd = new String[fCount];
            
            for (int i = 0; i < fCount; i++) { 
                String s = br.readLine(); 
                if (s==null) {
                    throw new IOException("EOF");
                }

                fwd[i]=s; 
            }

            for (int i = fCount-1; i >= 0; i--) {
                forwardStack.push(fwd[i]);
            }

            // HISTORY
            String hHdr = br.readLine();
            
            if (hHdr == null || !hHdr.startsWith("HISTORY ")) {
                throw new IOException("Bad file format");
            }

            int hCount = Integer.parseInt(hHdr.substring(8).trim());

            for (int i = 0; i < hCount; i++) {
                String s = br.readLine();

                if (s==null) {
                    throw new IOException("EOF");
                }

                historyQueue.offer(s);
            }

        } catch (Exception e) {
            return "Failed to restore session: " + e.getMessage();
        }
        
        return "Previous session restored.";
    }
}
