package hairdressingsalon;

/**
*
* @author Oguzhan Tohumcu
*/
public class Client {
   private Monitor hairSalon;
   
   public Client(String name, Monitor theSalon) {
       super(name);
       hairSalon = theSalon;
   }
   
   public void run(){
       while (true) {	
           try{
               justLive();
               hairSalon.haveAHaircut();
           } 
           catch (InterruptedException e){};
       }
   }

   private void justLive(){
       try {
           System.out.println(getName() + " arrived");
           Thread.sleep((int)(Math.random() * 1000));
       } 
       catch (InterruptedException e) {}
   }

   public String getClientName(){
       return getName();
   }
}

