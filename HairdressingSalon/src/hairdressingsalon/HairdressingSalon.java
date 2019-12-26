package hairdressingsalon;

/**
*
* @author Oguzhan Tohumcu
*/
public class HairdressingSalon {

   //Number of Hairdresser is fixed to 1
   private static final int NR_OF_CUSTOMERS = 3;
   
   public static void main(String[] args) {

       Monitor hairSalon = new Monitor();
       Thread [] client;
       Thread hairdresser;
       client = new Thread[NR_OF_CUSTOMERS];

       for(int i = 0; i < NR_OF_CUSTOMERS; i++){
           client[i] = new Client("c"+i, hairSalon);
           client[i].start();
       }
       hairdresser = new Hairdresser(hairSalon);
       hairdresser.start();
   }
}
