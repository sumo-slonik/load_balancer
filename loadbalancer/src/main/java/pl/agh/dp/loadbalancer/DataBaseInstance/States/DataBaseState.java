package pl.agh.dp.loadbalancer.DataBaseInstance.States;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.agh.dp.loadbalancer.DataBaseInstance.DataBaseInstance;


public abstract class DataBaseState {

    public abstract void loseConnection(DataBaseInstance databaseInstance);
    public abstract void establishConnection(DataBaseInstance databaseInstance);
    public abstract DataBaseStates getState();
    public abstract boolean isConnected();
    public abstract void queryProcessorHandle();

    public void handleInsert(String insertQuery, Session databaseSession){
        if(! insertQuery.toLowerCase().startsWith("insert")){
            return;
        }
        Query query;
        if(insertQuery.toLowerCase().contains("values")){ // insert into values case
            System.out.println("jestem w insercie");
            System.out.println(insertQuery);
            query = databaseSession.createSQLQuery(insertQuery);
        }
        else{
            return;
        }
        if(query !=  null) {
            query.executeUpdate();
        }
    }
}
