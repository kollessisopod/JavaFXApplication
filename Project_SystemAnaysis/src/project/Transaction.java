package project;
import java.util.Vector;
import java.io.Serializable;
import java.time.LocalDateTime;  

public class Transaction implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   LocalDateTime now = LocalDateTime.now();
	   String transactionTime = now.toString();
	   
	    private Vector<TransactionElement> transactionElements;
	    private LocalDateTime dateTime;

	    public Transaction() {
	        this.transactionElements = new Vector<>();
	        this.dateTime = LocalDateTime.now();
	    }

	    public void addTransactionElement(TransactionElement element) {
	        transactionElements.add(element);
	        System.out.println("transaction added>" + element.getQuantity() + element.getProduct().getProductID());
	    }

	    public Vector<TransactionElement> getTransactionElements() {
	        return transactionElements;
	    }

	    public LocalDateTime getDateTime() {
	        return dateTime;
	    }
	   
	    
	    
}
