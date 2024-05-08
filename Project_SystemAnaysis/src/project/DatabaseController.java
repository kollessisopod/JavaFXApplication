package project;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DatabaseController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Map<String, String> branchPersonnel = new HashMap<>();
	Map<String, String> branchExecutive = new HashMap<>();	
	Map<String, String> chiefExecutive = new HashMap<>();	

	
    public void saveState(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(branchPersonnel);
            oos.writeObject(branchExecutive);
            oos.writeObject(chiefExecutive);
            
            
            
            System.out.println("Save Successful.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	@SuppressWarnings("unchecked")
	public void loadState(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      
        	branchPersonnel = (Hashtable<String, String>) ois.readObject();
        	branchExecutive = (Hashtable<String, String>) ois.readObject();
        	chiefExecutive = (Hashtable<String, String>) ois.readObject();

            System.out.println("Load Successful.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load Failure.");
            e.printStackTrace();
        }
    }
    
    
	
	
}
