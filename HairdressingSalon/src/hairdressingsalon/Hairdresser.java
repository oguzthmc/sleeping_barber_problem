package hairdressingsalon;

/**
*
* @author Oguzhan Tohumcu
*/

public class Hairdresser {
   private Monitor hairSalon;
   private Client client;

   public Hairdresser(Monitor theSalon){
       hairSalon = theSalon;
   }
   public void run(){
       while (true) {
           try {
               client = hairSalon.nextClient();				
               cut();
               hairSalon.showOut(client);
           } 
           catch (InterruptedException exception){}
       }
   }
   private void cut(){
       try {
           System.out.println("barber cuts " + client.getClientName());
           Thread.sleep((int)(Math.random() * 100));
       } 
       catch (InterruptedException exception) {}			
   }
}

