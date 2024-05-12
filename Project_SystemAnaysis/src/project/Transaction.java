package project;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.io.Serializable;
import java.time.LocalDateTime;  

public class Transaction implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	   LocalDateTime now = LocalDateTime.now();
	   String transactionTime = dtf.format(now);
	   
	    private Vector<TransactionElement> transactionElements;
	    private LocalDateTime dateTime;

	    public Transaction() {
	        this.transactionElements = new Vector<>();
	        this.dateTime = LocalDateTime.now();
	    }

	    public void addTransactionElement(TransactionElement element) {
	        transactionElements.add(element);
	    }

	    public Vector<TransactionElement> getTransactionElements() {
	        return transactionElements;
	    }

	    public LocalDateTime getDateTime() {
	        return dateTime;
	    }
	   
	    
	    
}
