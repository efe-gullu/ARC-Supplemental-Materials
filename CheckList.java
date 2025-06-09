package ARC_Supplemental_Materials;
import java.util.ArrayList;
import java.util.Scanner;


public class CheckList {
	
	static Scanner scanner = new Scanner(System.in);         //creating a scanner object to read user input
	
	static class Questions{                              //creating a Questions class with attributes "description" and "isChecked"
		String description;                             //this way, questions can be stored with the information whether they are checked or not
		boolean isChecked;
		
		public Questions(String desc) {               //initializing attributes
			description=desc;
			isChecked=false;
		}
	}
	
	static class Battery{                       //creating a "Battery" class to store battery objects
		String name;
		double voltage;
		String health;
		
		public Battery(String nm, double volt, String he) {      //initializing attributes.
			name=nm;
			voltage=volt;
			health=he;
		}
	}
	
	static boolean running = true;          // will be used as a loop condition for the while loop in the main method
	
	public static void main(String[] args) {
		
		boolean batteryChosen=false;                  //initially, no battery is chosen. Will be used to determine if the user can exit the program (battery should be chosen).
		ArrayList<Questions> questionList = new ArrayList<Questions>();   // creating lists to store the checklist questions
		ArrayList<Battery> batteries = new ArrayList<Battery>();     // and the available batteries
		
		Battery battery1 = new Battery("Akü 1", 12.1, "ortalama");       
		Battery battery2 = new Battery("Akü 2", 11.5, "iyi");
		Battery battery3 = new Battery("Akü 3", 13.9, "iyi");
		batteries.add(battery1); batteries.add(battery2); batteries.add(battery3);   // creating example battery objects and adding them to the list
		
		
       
        while (running) {
        	
        	System.out.println("- Maç Öncesi Hazırlık Listesine Hoş Geldiniz. Lütfen:");       //the directory menu that will come up after the user's every action. 
    		System.out.println("- Kontrol edilmesini istediğiniz bir madde yazmak için \"a\",");
            System.out.println("- Maddelerin kontrolüne ve akü seçimine geçmek için \"b\", ");
            System.out.println("- Programdan çıkış yazmak için ise \"c\" yazınız.");
            System.out.print("Seçiminiz: ");
            
            String choice = scanner.nextLine().trim().toLowerCase();   //reading user input for their choice indicating the action they want to perform. 
            
           
            if (choice.equals("a")) {
        		addQuestion(questionList);
        	}
        	else if (choice.equals("b")) {
        		controlList(questionList);
        		chooseBattery(batteries);
        		batteryChosen=true;            //if the option "b" is selected, battery will be chosen.
        		controlUnchecked(questionList);
        	}
        	else if (choice.equals("c")) {
        		exit(questionList, batteryChosen);
        		
        	}
        	else {
        		System.out.println("Geçersiz seçim. Lütfen tekrar deneyiniz.");  //if the user inputs an answer other than "a", "b", or "c" an error message is thrown.
        	}
        }
	}
	
	public static void addQuestion (ArrayList<Questions> list) {
		System.out.println("Lütfen kontrol edilmesini istediğiniz maddeyi giriniz: ");
		String desc = scanner.nextLine();         //reading user input to get the description of the question they want to check. 
		list.add(new Questions(desc));            //creating a Questions object with their input and adding it to the list of questions.
		
	}
	
	public static void controlList (ArrayList<Questions> list) {
		int order = 1;                                  //variable to keep track of the order of questions in the output. 
		while (list.size()<3) {                         //the minimum number of questions needed to start the checking process is 3.
			System.out.println("Kontrol edilecek soru sayısı yetersiz, lütfen yeni soru ekleyin:");
			addQuestion(list);                  //if the number of questions is insufficient, ask the user to add questions until there are at least 3 questions in the list.
		}
		
		System.out.println("Kontrole başlanılıyor.");
		System.out.println("Eğer bir madde kontrol edilmişse \"evet\", yoksa \"hayır\" yazın.");
		for (Questions item : list) {               //traversin every element of the list of questions.
			System.out.println(order+". "+ item.description +"\n Kontrol edildi mi? (evet/hayır)");
			String ans = scanner.nextLine().toLowerCase();
			if (ans.equals("evet")) {
				item.isChecked = true;  //if the user inputs "evet", the isChecked attribute of the Question object is set to true.
			}
			order++;
			
		}
		
		
	}
	
	public static boolean chooseBattery(ArrayList<Battery> list) { // While exiting the program, to make sure that a battery is choosen, this method's return type is boolean.
		double diff1;           //initializing variables to represent the difference between the voltages of the batteries and 13.0.                                  
		double diff2;
		Battery best = list.get(0);    //the best battery is the first one until it's compared with the next ones in the list.
		
		for (int i =1; i < list.size(); i++) {  //traversing starts from the second element (i=1) since we are comparing element with the element coming before.
			diff1= Math.abs(13.0-best.voltage);   //the values are determined by their distance from 13.0 (absolute value).
			diff2= Math.abs(13.0-list.get(i).voltage);
			
			if (diff2 < diff1) {
				best = list.get(i);
			}//no if statement written for the case when the current best battery has the closer voltage to 13.0, because we wouldn't need to do any change in that case.
			
			else if (diff2 == diff1) {
				if (list.get(i).health.equals("iyi") && (best.health.equals("iyi")|| best.health.equals("ortalama"))) {   //if the differences of voltages from 13.0 are same, the one with "good" health status is the better one.
					best = list.get(i);                                                                     //if both batteries have a health status of "good", it doesn't matter which one is chosen.
				}                                                                                           //but in this program, the battery being traversed will be the best battery in these cases.
				else if (list.get(i).health.equals("ortalama") && best.health.equals("ortalama")) {
					best = list.get(i);
				} //no if statement written for the case where the battery being traversed has "ortalama" health status and the current best one has "good" health status because we wouldn't need to do any change.
			}       
		}
		System.out.println("Verilen aküler arasından en uygun olanı "+ "\"" +best.name+ "\""+ " adlı aküdür. \n");     //go the next line for a better look in the console.
	      
		return true;               //the battery is chosen now. This statement will be useful in the "exit" method
	}
	
	
	
	public static boolean controlUnchecked(ArrayList<Questions> list) {
		
		while (true) {      //an infinite loop is created since the unchecked question will be asked over and over again until every question is marked as checked.
			boolean anyUnchecked = false;   //a boolean variable to find out if there is any unchecked question in the list.
			
			for (Questions item : list) {     //traversing every item in the question list.
				
				if (!item.isChecked) {      //if at least one question is NOT checked, 
					anyUnchecked = true;    //the statement is set to true, and
					System.out.println("Tekrardan Kontrol: " + item.description + "(evet/hayır):");
					String input = scanner.nextLine().trim().toLowerCase();      //ask this question again to the user.
					
					if (input.equals("evet")) {    
						item.isChecked=true;      //the question is checked, changing the attribute so it won't come back in the next round.
						System.out.println("Madde tamamlandı olarak işaretlendi.");
					} else {
						System.out.println("Madde tamamlanmadı.");  //if it's not checked, do nothing. The question will be asked again.
					}
		        }
		    }

			
		    if (!anyUnchecked) {     //if there is NOT an instance where a question from the list is unchecked, 
		    	System.out.println("Tüm maddeler kontrol edildi.");
		    	return true;     //the method is completed, loop stops.
		    } else {
		    	System.out.println("Henüz kontrol edilmemiş maddeler var. Tekrar kontrol ediliyor."); //if there is, loop continues.
		    }
		}		
	}
	
	public static boolean exit(ArrayList<Questions> list, boolean batteryChosen) {    //method to if the program satisfies every requirement before exiting.
		
		boolean allChecked = true;   //a boolean variable to find out if there is any unchecked question in the list.
		
		for (Questions item : list) { //traversin every element of the question list.
			
			if (!item.isChecked) {
				allChecked = false; //if there is one, statement set to false.
			}
		}
		if (list.size()==0) {  //1: if no question is added to the list, the user can't exit the program.
			System.out.println("Hiç madde eklenmemiş, çıkış yapılamaz. Lütfen madde ekleyin.");
			return false; //exit failed.
		}
		
		if (!allChecked) {  //2: if there is at least one unchecked question, the user can't exit the program.
			System.out.println("Tüm maddeler tamamlanmamış, çıkış yapılamaz.Lütfen tüm maddelerin tamamlandığından emin olup tekrar deneyin.");
			return false; //exit failed.
		}
		if (!batteryChosen) { //3: if the battery is not chosen yet, the user can't exit the program.
			System.out.println("Akü seçilmedi, çıkış yapılamaz. Lütfen en uygun akünün seçildiğinden emin olup tekrar deneyin.");
			return false; //exit failed.
		}
		
		System.out.println("MAÇA HAZIR !!! BOL ŞANS");  //if the conditions above are satisfied, the user can exit the program.
		running=false;  //the loop condition of the while loop in the main method is set to false, the loop stops.
		return true;  //exit successful.
	}
	
	
}


