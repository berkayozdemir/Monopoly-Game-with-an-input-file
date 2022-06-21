import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static  Card[] CardList=new Card[40];
    public static Chance chance;
    public static Community_Chest community_chest;
    public static  Player  Player1= new Player("Player 1",15000,1);
    public static Player  Player2= new Player("Player 2",15000,2);
    public static Banker  banker= new Banker("Banker",100000);
    public static String lastCommand ="";




    static void readCommands() {
        try {
            File myObj = new File("command3.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(!data.equals("show()")) {
                    String[] arr=data.split(" ",2);
                    String[] arr2=arr[1].split(";",2);
                    int a=Integer.parseInt(arr2[0]);
                    int b=Integer.parseInt(arr2[1]);
                    playGame(a,b);
                    lastCommand="play";



                }

                else{
                    lastCommand="show";
                   show();

                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if(lastCommand.equals("play")) {show();}

    }

    public static void show() {

        ArrayList<String> player1props=new ArrayList<>();
        ArrayList<String> player2props=new ArrayList<>();

        for(int i=0;i<Player1.properties.size();i++) {
            player1props.add(Player1.properties.get(i).getName());

        }
        for(int i=0;i<Player2.properties.size();i++) {
            player2props.add(Player2.properties.get(i).getName());

        }



        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Player 1\t"+Player1.getMoney()+"\t"+"have: ");
        if (player1props.size() >= 1) {
            System.out.print(player1props.get(0));
        }


        for (int i = 1; i < player1props.size();i++) {

            System.out.print(", " + player1props.get(i)); }

        System.out.println();



        System.out.print("Player 2\t"+Player2.getMoney()+"\t"+"have: ");

        if (player2props.size() >= 1) {
            System.out.print(player2props.get(0));
        }


        for (int i = 1; i < player2props.size();i++) {

            System.out.print(", " + player2props.get(i)); }


        System.out.println();
        System.out.println("Banker: "+banker.getMoney());
        if(Player1.getMoney()>Player2.getMoney()) {
            System.out.println("Winner  Player 1");
        }

        else if(Player2.getMoney()>Player1.getMoney()) {
            System.out.println("Winner  Player 2");
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");





    }

    public static String showStatus(Player thePlayer,int b) {
        String a="Player "+thePlayer.id+"\t"+b+"\t"+(thePlayer.location+1)+"\t"+Player1.getMoney()+
                "\t"+Player2.getMoney()+"\t"+"Player "+ thePlayer.id;
        return a;
    }

    public static int numberofRailRoads(Player player) {
        int a=0;
        for (int i=0;i<player.properties.size();i++) {if (player.properties.get(i).getType().equals("railroad")) {

            a++;
        }
        }
        return a;
    }

    public static String propertyWorks(Player thePlayer, Player otherPlayer, int b) {

        Property property=(Property) CardList[thePlayer.location];
        if(property.owner==0 && thePlayer.getMoney()>=property.getCost()) {
            property.owner=thePlayer.id;
            thePlayer.setMoney(thePlayer.getMoney()-property.getCost());
            thePlayer.properties.add(property);
            banker.takeMoney(property.getCost());
           return " bought "+property.getName();


        }
        else if(property.owner==0 && thePlayer.getMoney()<property.getCost()) {
            System.out.println(showStatus(thePlayer,b)+" goes bankrupt");
            show();
            System.exit(1);
        }
         if(thePlayer.id==property.owner) {
            return " has "+property.getName();
        }

        else if( thePlayer.id!=property.owner) {
           boolean canPay=true;

            if(property.getType().equals("railroad")) {

                int railRoads=numberofRailRoads(otherPlayer);
                int rent=railRoads*25/100*property.getCost();
                if(thePlayer.getMoney()>=rent)
                {thePlayer.payMoney(rent);
                otherPlayer.takeMoney(rent);}
                else {canPay=false;}

            }
            else if(property.getType().equals("land")) {

                int cost=property.getCost();
                int pay;

                if(cost<=2000) {pay=cost/100*40;}
                else if(  cost <=3000 && cost>2000) {pay=cost/100*30;}
                else  {pay=cost/100*35;}

                if(thePlayer.getMoney()>=pay)
                {thePlayer.payMoney(pay);
                    otherPlayer.takeMoney(pay);}
                else {canPay=false;}
            }

            else if (property.getType().equals("company")) {

                int cost=property.getCost();
                int pay=4*b*cost/100;

                if(thePlayer.getMoney()>=pay)
                {thePlayer.payMoney(pay);
                    otherPlayer.takeMoney(pay);}
                else {canPay=false;}



            }
            if(!canPay) {
                System.out.println(showStatus(thePlayer,b)+" goes bankrupt");
                show();
                System.exit(1);}

           return " paid rent for "+property.getName();

        }

        return "";



    }

    public static String communityWorks(Player thePlayer,Player otherPlayer,int b) {
        int community_number=community_chest.getNumber();
        community_chest.moveNumber();

        if(community_chest.communityList.get(community_number).equals("Advance to Go (Collect $200)")) {
            thePlayer.location=0;

            thePlayer.takeMoney(200);
            banker.payMoney(200);
           return " draw Community Chest -"+"advance to go";
        }

        else if(community_chest.communityList.get(community_number).equals("Bank error in your favor - collect $75")) {


            thePlayer.takeMoney(75);
            banker.payMoney(75);
            return " draw Community Chest -"+"Bank error in your favor";
        }

        else if(community_chest.communityList.get(community_number).equals("Doctor's fees - Pay $50")) {


            thePlayer.payMoney(50);
            banker.takeMoney(50);
           return " draw Community Chest -"+"Doctor's fees";
        }

        else if(community_chest.communityList.get(community_number).equals("It is your birthday Collect $10 from each player")) {

            thePlayer.takeMoney(10);
            otherPlayer.payMoney(10);
           return " draw Community Chest -"+"It is your birthday Collect $10 from each player";

        }

        else if(community_chest.communityList.get(community_number).equals("Grand Opera Night - collect $50 from every player for opening night seats")) {


            thePlayer.takeMoney(50);
            otherPlayer.payMoney(50);
            return " draw Community Chest -"+"Grand Opera Night";

        }

        else if(community_chest.communityList.get(community_number).equals("Income Tax refund - collect $20")) {


            thePlayer.takeMoney(20);
            banker.payMoney(20);
          return " draw Community Chest -"+"Income Tax refund";
        }

        else if(community_chest.communityList.get(community_number).equals("Life Insurance Matures - collect $100")) {


            thePlayer.takeMoney(100);
            banker.payMoney(100);
            return " draw Community Chest -"+"Life Insurance Matures";
        }

        else if(community_chest.communityList.get(community_number).equals("Pay Hospital Fees of $100")) {


            thePlayer.payMoney(100);
            banker.takeMoney(100);
            return " draw Community Chest -"+"Pay Hospital Fees of $100";
        }

        else if(community_chest.communityList.get(community_number).equals("Pay School Fees of $50")) {


            thePlayer.payMoney(50);
            banker.takeMoney(50);
            return " draw Community Chest -"+"Pay School Fees of $50";
        }

        else if(community_chest.communityList.get(community_number).equals("You inherit $100")) {


            thePlayer.takeMoney(100);
            banker.payMoney(100);
            return " draw Community Chest -"+"You inherit $100";
        }

        else if(community_chest.communityList.get(community_number).equals("From sale of stock you get $50")) {


            thePlayer.takeMoney(50);
            banker.payMoney(50);
            return " draw Community Chest -"+"From sale of stock you get $50";
        }
return "";
    }

    public static String chanceWorks(Player thePlayer,Player otherPlayer,int b) {
        int chanceNumber=chance.getNumber();
        chance.moveNumber();

        if(chance.chanceList.get(chanceNumber).equals("Advance to Go (Collect $200)")) {
            thePlayer.location=0;
            thePlayer.takeMoney(200);
            banker.payMoney(200);
            return " Advance to Go (Collect $200)";

        }

        else if(chance.chanceList.get(chanceNumber).equals("Advance to Leicester Square")) {
            thePlayer.location=26;
            String leicester="";

            if(((Property)CardList[26]).getName().equals("Leicester Square")) {
                Property property=(Property) CardList[thePlayer.location];
                if(property.owner==0 && thePlayer.getMoney()>=property.getCost()) {
                    property.owner=thePlayer.id;
                    thePlayer.setMoney(thePlayer.getMoney()-property.getCost());
                    banker.takeMoney(((Property) CardList[26]).getCost());
                    thePlayer.properties.add(property);

                    leicester="Player "+thePlayer.id+" bought Leicester Square";
                }
                else if(property.owner== thePlayer.id) {
                    leicester="Player "+thePlayer.id+" has Leicester Square";

                }
                else {

                    leicester="Player "+thePlayer.id+" paid rent for Leicester Square";
                    thePlayer.payMoney(780);
                    otherPlayer.takeMoney(780);
                }



            }
            return " Advance to Leicester Square "+leicester;

        }

        else if(chance.chanceList.get(chanceNumber).equals("Go back 3 spaces")) {
            thePlayer.location-=3;
            String answer="";

            if(CardList[thePlayer.location]._class.equals("property")) {
                 answer=propertyWorks(thePlayer,otherPlayer,b);


            }

            else if(CardList[thePlayer.location]._class.equals("other")) {
                answer=otherWorks(thePlayer,otherPlayer,b);


            }

            else if (CardList[thePlayer.location]._class.equals("community")) {

                answer=communityWorks(thePlayer,otherPlayer,b);

            }

            else if (CardList[thePlayer.location]._class.equals("chance")) {

                answer=chanceWorks(thePlayer,otherPlayer,b);

            }


            return " Go back 3 spaces "+answer;
        }

        else if(chance.chanceList.get(chanceNumber).equals("Pay poor tax of $15")) {
            thePlayer.payMoney(15);
            banker.takeMoney(15);
            return " Pay poor tax of $15";
        }

        else if(chance.chanceList.get(chanceNumber).equals("Your building loan matures - collect $150")) {
            thePlayer.takeMoney(150);
            banker.payMoney(150);
            return " Your building loan matures - collect $150";
        }

        else if(chance.chanceList.get(chanceNumber).equals(" You have won a crossword competition - collect $100")) {
            thePlayer.takeMoney(100);
            thePlayer.payMoney(100);
            return "  You have won a crossword competition - collect $100";
        }


return "";
    }

    public static  String otherWorks(Player thePlayer,Player otherPlayer,int b) {
        if(thePlayer.location==0) {

           return " is in GO square";}

        else if(thePlayer.location==10) {

            if(!thePlayer.onJail) {thePlayer.onJail=true; thePlayer.jailCount=3;}
            return " went to jail";
        }

        else if(thePlayer.location==20) {

            return " free parking";
        }

        else if(thePlayer.location==30) {
            thePlayer.location=10;
            if(!thePlayer.onJail) {thePlayer.onJail=true; thePlayer.jailCount=3;}
          return " went to jail";

        }

        else if(thePlayer.location==38) {


            thePlayer.payMoney(100);
            banker.takeMoney(100);
            return " paid tax";
        }

        else if(thePlayer.location==4) {


            thePlayer.payMoney(100);
            banker.takeMoney(100);
            return  " paid tax";
        }
    return "";

    }



    public static void playGame(int a,int b) {

        Player thePlayer;
        Player otherPlayer;

        if(a==1) {thePlayer=Player1; otherPlayer=Player2;}
        else {thePlayer=Player2; otherPlayer=Player1;}
        if(thePlayer.getMoney()<=0) {
            System.out.println(showStatus(thePlayer,b)+" goes bankrupt");
            show();
            System.exit(1);
        }
        if(thePlayer.jailCount==0) {  thePlayer.move(b);}
        else {
            System.out.println(showStatus(thePlayer,b)+" in jail (count="+(4- thePlayer.jailCount)+")");
            thePlayer.jailCount--;
            return;
        }





        if(CardList[thePlayer.location]._class.equals("property")) {
            String answer=propertyWorks(thePlayer,otherPlayer,b);
            System.out.println(showStatus(thePlayer,b)+answer);

        }

        else if(CardList[thePlayer.location]._class.equals("other")) {
            String answer=otherWorks(thePlayer,otherPlayer,b);
            System.out.println(showStatus(thePlayer,b)+answer);

        }

        else if (CardList[thePlayer.location]._class.equals("community")) {

            String answer=communityWorks(thePlayer,otherPlayer,b);
            System.out.println(showStatus(thePlayer,b)+answer);
        }

        else if (CardList[thePlayer.location]._class.equals("chance")) {

            String answer=chanceWorks(thePlayer,otherPlayer,b);
            System.out.println(showStatus(thePlayer,b)+answer);
        }


    }

    public static void main(String[] args) {

        PrintStream fileOut = new PrintStream("monitoring.txt");
        System.setOut(fileOut);
        
        PropertyJsonReader propertyJsonReader=new PropertyJsonReader(CardList);
        chance=new Chance("Chance",1);
        community_chest=new Community_Chest(("Community Chest"),1);


        ListJsonReader listJsonReader=new ListJsonReader(chance,community_chest);


        CardList[7]=chance;
        CardList[22]=chance;
        CardList[36]=chance;

        CardList[2]=community_chest;
        CardList[17]=community_chest;
        CardList[33]=community_chest;

        Other other=new Other("go",1);
        Other other2=new Other("jail",11);
        Other other3=new Other("free parking",21);
        Other other4=new Other("go to jail",31);
        Other other5=new Other("tax",39);
        Other other6=new Other("tax",5);

        CardList[0]=other;
        CardList[10]=other2;
        CardList[20]=other3;
        CardList[30]=other4;
        CardList[38]=other5;
        CardList[4]=other6;


        readCommands();




        for(int i=0;i<40;i++) {
            if(CardList[i]!=null) {
                //System.out.println(CardList[i].getName()+i);
            }
        }







    }

}
