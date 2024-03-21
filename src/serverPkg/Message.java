package serverPkg;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable{
	private String fromAcctNum;
    private String destinationID;
    private String message;
    private Date date;
    private MsgType msgType;
    //private int id;   Add a id to message to store in HashMap


    //Default Constructor
    public Message() {
    	this(null, null, null);
    	this.date = null;
    }
    
    // constructor
    public Message(String fromAcctNum, String destinationID, String message){
        this.fromAcctNum = fromAcctNum;
        this.destinationID = destinationID;
        this.date = new Date();
        this.message = message;
    }


    /**************** Getters ***********************/
    public String getFrom(){
    	return fromAcctNum;
    }

    public String getTo(){
    	return destinationID;
    }

    public MsgType getMsgType(){
    	return msgType;
    }
    
    public String getString() {
    	return message;
    }

    public Date getDate() {
    	return date;
    }
}