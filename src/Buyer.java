import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Project 4 - Buyer
 * This class extends the user class giving it the permissions that come with being a buyer.
 *
 * @author Kevin Zhang, Jalen Mann, Alimzhan Sultanov, Kyle Griffin, and PJ Henwood, lab sec LC2
 *
 * @version November 15, 2022
 *
 */

public class Buyer extends User {

    //basic constructors
    public Buyer (String username, String email, String password) {
        super(username, email, password);
    }

    public Buyer (String username, String email, String password, ArrayList<String> blockedUsernames) {
        super(username, email, password, blockedUsernames);
    }
    
    public String viewStatistics(boolean alphabetical) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader("stores.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        if (fr != null) {
            br = new BufferedReader(fr);
        }
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<String> stores = new ArrayList<>();
        ArrayList<Integer> totalMessagesReceived = new ArrayList<>();
        ArrayList<Integer> messagesFromUser = new ArrayList<>();
        //creates ArrayLists messages and stores
        String line;
        String store;
        line = br.readLine();
        while (line != null) {
            store = line.substring(1, line.indexOf(',') - 1);
            messages.add(line.substring(1, line.length() - 1));
            if (!stores.contains(store)) {
                stores.add(store);
            }
            line = br.readLine();
        }
        //creates ArrayList totalMessagesReceived
        for (int i = 0; i < messages.size(); i++) {
            line = messages.get(i);
            store = line.substring(0, line.indexOf(",") - 1);
            line = line.substring(line.indexOf(",") + 1);
            int messagesReceived = Integer.parseInt(line.substring(1, line.indexOf(",") - 1)) ;
            totalMessagesReceived.add(messagesReceived);
        }
        //creates ArrayList messagesFromUser
        for (int i = 0; i < stores.size(); i++) {
            int counter = 0;
            for (int j = 0; j < messages.size(); j++) {
                line = messages.get(j);
                String user = line.substring(line.indexOf(',', line.indexOf(',')));
                store = line.substring(0, line.indexOf(','));
                if (user.equals(this.getUsername()) && store.equals(stores.get(i))) {
                    counter++;
                }
            }
            messagesFromUser.add(counter);
        }
        //create hashmaps listOne and listTwo
        HashMap<String, Integer> listOne = new HashMap<>();
        for (int i = 0; i < stores.size(); i++) {
            listOne.put(stores.get(i), totalMessagesReceived.get(i));
        }
        HashMap<String, Integer> listTwo = new HashMap<>();
        for (int i = 0; i < stores.size(); i++) {
            listTwo.put(stores.get(i), messagesFromUser.get(i));
        }
        //sort alphabetically
        Collections.sort(stores);
        StringBuilder sortMessages = new StringBuilder();
        if (alphabetical) {
            //alphabetical
            for (int i = 0; i < stores.size(); i++) {
                sortMessages.append("Store: " + stores.get(i) + " - Number of messages received: " + listOne.get(stores.get(i)) + "\\n");
            }
        } else {
            //reverse alphabetical
            for (int i = stores.size() - 1; i >= 0; i--) {
                sortMessages.append("Store: " + stores.get(i) + " - Number of messages received: " + listOne.get(stores.get(i)) + "\\n");
            }
        }
        return sortMessages.toString();
    }

    public String toString() {
        String blockedList = "";
        if (getBlockedUsers().size() > 0) {
            for (User user : getBlockedUsers()) {
                blockedList += user.getUsername() + ",";
            }
            blockedList = blockedList.substring(0, blockedList.length() - 1);
        }
        String ans = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", getUsername(), getEmail(),
                getPassword(), "b", blockedList);
        return ans;
    }
    
}
