import java.text.*;
import java.util.*;
import javax.swing.*;
import java.util.ArrayList; 
import java.io.*;
public class Project4
{
	public static ArrayList<Game> game;
	public static ArrayList<Developer> developer;
	public static ArrayList<Genre> genre;
	public static ArrayList<Sale> sales;
	public static void main(String [] args) throws IOException
	{
		readGenre();
		readDevelopers();
		readGameDetails();
		readSales();
		menu();
	}
	public static void menu() throws IOException
	{
		String selectedMenuItem;
		boolean exit = false;
		Object menuOptions [] = {"1.Montly Game Sales Report", "2.Montly Genre Sales Report", "3.Monthly Sales for a Developer",
								 "4.Total Sales on Games(To Date)", "5.Total Sales on Genre(To Date)", "0.Quit"};
		do
		{
			selectedMenuItem = (String) JOptionPane.showInputDialog(null, "Choose one", "Select Option", 1, null, menuOptions, menuOptions[0]);
			if(selectedMenuItem == menuOptions[0])
				monthlyGameSales();
			else if (selectedMenuItem == menuOptions[1])
				monthlyGenreSales();
			else if (selectedMenuItem == menuOptions[2])
				monthlySalesDeveloper();
			else if (selectedMenuItem == menuOptions[3])
				totalGameSales();
			else if (selectedMenuItem == menuOptions[4])
				totalGenreSales();
			else 
				exit = true;
		}
		while (!exit);
	}
	public static void readGenre() throws IOException
	{
		genre = new ArrayList <Genre>();
		File aFileReader = new File("GamesGenre.txt");
		Scanner in = new Scanner(aFileReader);
		Genre aGenre;
		while(in.hasNext())
		{
			String [] temp = in.nextLine().split(",");
			int genreNum = Integer.parseInt(temp[0]);
			String gameGenre = temp[1];
		    aGenre = new Genre(genreNum, gameGenre);
		    genre.add(aGenre);
		}
		in.close();
	}
	public static void readGameDetails() throws IOException
	{
		game = new ArrayList <Game>();
		File aFileReader = new File("GamesDetails.txt");
		Scanner in = new Scanner(aFileReader);
		Game aGame;
		while(in.hasNext())
		{
			String [] temp = in.nextLine().split(",");
			int gameID = Integer.parseInt(temp[0]);
			String gameTitle = temp[1];
			int gameDeveloper = Integer.parseInt(temp[2]);
			int gameGenre = Integer.parseInt(temp[3]);
			double gamePrice = Double.parseDouble(temp[4]);
			aGame = new Game(gameID, gameTitle, gameDeveloper, gameGenre, gamePrice);
			game.add(aGame);
		}
		in.close();
	}
	public static void readDevelopers() throws IOException
	{
		developer = new ArrayList <Developer>();
		File aFileReader = new File("GameDevelopers.txt");
		Scanner in = new Scanner(aFileReader);
		Developer aDeveloper;
		while(in.hasNext())
		{
			String [] temp = in.nextLine().split(",");
			int gameID = Integer.parseInt(temp[0]);
			String gameDeveloper = temp[1];
			aDeveloper = new Developer(gameID, gameDeveloper);
            developer.add(aDeveloper);			
		}
		in.close();
	}
	public static void readSales() throws IOException
	{
		sales = new ArrayList <Sale>();
		File aFileReader = new File("GameSales.txt");
		Scanner in = new Scanner(aFileReader);
		Sale aSale;
		while(in.hasNext())
		{
			String [] temp = in.nextLine().split(",");
			String date = temp[0];
			int gameUnits = Integer.parseInt(temp[1]);
			int gameID = Integer.parseInt(temp[2]);
			aSale = new Sale(date, gameUnits, gameID);
			sales.add(aSale);
		}
		in.close();
	}
	public static void monthlyGameSales()
	{
		NumberFormat aFormatter = NumberFormat.getCurrencyInstance();
		int i = 0;
		String [] months = {"January : ", "February : ", "March : ", "April : ", "May : ", "June : ", "July : ", "August : ", "September : ", "October : ", "November : ", "December : "};
		String [] date = new String [3];
		double gamePrice = 0;
		double totalSales = 0;
		int units = 0;
		int totalUnits = 0;
		double totalPrice = 0;
		int gameID = 0;
		String gameTitles[] = new String[game.size()];
		for (i = 0; i < gameTitles.length; i++)
			gameTitles[i] = game.get(i).getGameTitle();
		String selectedMenuItem = (String) JOptionPane.showInputDialog(null, "Choose one", "Game Titles", 1, null, gameTitles, gameTitles[0]);
		if (selectedMenuItem != null)
		{
			for(i = 0; i < gameTitles.length; i++)
			{
				if(selectedMenuItem == gameTitles[i])
				{
					gameID = game.get(i).getGameID();
					gamePrice = game.get(i).getGamePrice();
				}
			}
			for (i = 0; i < sales.size(); i++)
			{
				if (sales.get(i).getGameID() == gameID)
				{
					date = sales.get(i).getSaleDate().split("/");
					if(sales.get(i).getGameID() == gameID && date[1].equals((sales.get(i).getSaleDate().split("/"))[1]))
					{
						units = sales.get(i).getSaleUnits() ;
						totalPrice = (units * gamePrice);
						int monthIndex = Integer.parseInt(date[1]);
						months[monthIndex - 1]  +=  "Units: " + units +  " " + "Total: " + aFormatter.format(totalPrice);
						totalSales = totalPrice * units;
					}
				}
			}
			JOptionPane.showMessageDialog(null, months);
			JOptionPane.showMessageDialog(null, "Total sales of game to date: " + aFormatter.format(totalSales));
		}
	}
	public static void monthlyGenreSales()
	{
		NumberFormat aFormatter = NumberFormat.getCurrencyInstance();
		int i = 0;
		String [] months = {"January : ", "February : ", "March : ", "April : ", "May : ", "June : ", "July : ", "August : ", "September : ", "October : ", "November : ", "December : "};
		String [] date = new String [3];
		double totalSales = 0;
		double gamePrice = 0;
		int units = 0;
		int totalUnits = 0;
		double totalPrice = 0;
		int genreID = 0;
		int gameID = 0;
		String gameGenre[] = new String[genre.size()];
		for (i = 0; i < gameGenre.length; i++)
			gameGenre[i] = genre.get(i).getGenreName();
		String selectedMenuItem = (String) JOptionPane.showInputDialog(null, "Choose one", "Genres", 1, null, gameGenre, gameGenre[0]);
		if (selectedMenuItem != null)
		{
			for(i = 0; i < gameGenre.length; i++)
			{
				if(selectedMenuItem.matches(genre.get(i).getGenreName()))
					genreID = genre.get(i).getGenreID();
			}
			for (i = 0; i < sales.size(); i++)
			{
				int currentGameID = sales.get(i).getGameID();
				if (game.get(currentGameID-1).getGenreID() == genreID)
				{
					gamePrice = game.get(currentGameID -1).getGamePrice();
					gameID = game.get(currentGameID-1).getGameID();
					if(sales.get(i).getGameID() == gameID)
					{
						date = sales.get(i).getSaleDate().split("/");
						units = sales.get(i).getSaleUnits() ;
						totalPrice = (units * gamePrice);
						int monthIndex = Integer.parseInt(date[1]);
						months[monthIndex - 1]  +=  "Units: " + units +  " " + "Total: " + aFormatter.format(totalPrice);
						totalSales = totalPrice * units;
					}
				}
			}
			JOptionPane.showMessageDialog(null, months);
			JOptionPane.showMessageDialog(null, "Total sales of game to date: " + aFormatter.format(totalSales));
		}
	}
	public static void monthlySalesDeveloper()
	{
		NumberFormat aFormatter = NumberFormat.getCurrencyInstance();
		int i = 0;
		String [] months = {"January : ", "February : ", "March : ", "April : ", "May : ", "June : ", "July : ", "August : ", "September : ", "October : ", "November : ", "December : "};
		String [] date = new String [3];
		double totalSales = 0;
		double gamePrice = 0;
		int units = 0;
		int totalUnits = 0;
		double totalPrice = 0;
		int developerID = 0;
		int gameID = 0;
		String gameDeveloper[] = new String[developer.size()];
		for (i = 0; i < gameDeveloper.length; i++)
			gameDeveloper[i] = developer.get(i).getDeveloperName();
		String selectedMenuItem = (String) JOptionPane.showInputDialog(null, "Choose one", "Developers", 1, null, gameDeveloper, gameDeveloper[0]);
		if (selectedMenuItem != null)
		{
			for(i = 0; i < gameDeveloper.length; i++)
			{
				if(selectedMenuItem.matches(developer.get(i).getDeveloperName()))
					developerID = developer.get(i).getDeveloperID();
			}
			for (i = 0; i < sales.size(); i++)
			{
				int currentGameID = sales.get(i).getGameID();
				if (game.get(currentGameID-1).getGenreID() == developerID)
				{
					gamePrice = game.get(currentGameID -1).getGamePrice();
					gameID = game.get(currentGameID-1).getGameID();
					if(sales.get(i).getGameID() == gameID)
					{
						date = sales.get(i).getSaleDate().split("/");
						units = sales.get(i).getSaleUnits() ;
						totalPrice = (units * gamePrice);
						int monthIndex = Integer.parseInt(date[1]);
						months[monthIndex - 1]  +=  "Units: " + units +  " " + "Total: " + aFormatter.format(totalPrice);
						totalSales = totalPrice * units;
					}
				}
			}
			JOptionPane.showMessageDialog(null, months);
			JOptionPane.showMessageDialog(null, "Total sales of game to date: " + aFormatter.format(totalSales));
		}
	}
	public static void totalGameSales() throws IOException
	{
		int temp1[];
		String temp2[];
		int temp;
		String templ;
		boolean fixed =false;
		String gameTitles[] = new String[game.size()];
		int gameSales[] = new int[game.size()];
		int units;
		String results = "";
		for (int i = 0; i < gameTitles.length; i++)
			gameTitles[i] = game.get(i).getGameTitle();
		for (int i = 0; i < game.size(); i++){
			int gameID = i+1;
			int totalUnits = 0;
			for (int j = 0; j < sales.size(); j++){
				if (sales.get(j).getGameID() == gameID){
					units = sales.get(j).getSaleUnits();
					totalUnits =totalUnits+ units;
				}
				gameSales[i]=totalUnits;
			}
			
		}temp1 = gameSales;
		temp2 = gameTitles;
		while(fixed==false){
			fixed = true;
		
		for (int i=0;i<temp1.length-1;i++){
			if (temp1[i]<temp1[i+1]){
				temp = temp1[i+1];
				templ = temp2[i+1];
				temp1[i+1]=temp1[i];
				temp2[i+1]=temp2[i];
				temp1[i]=temp;
				temp2[i]=templ;
				fixed=false;
			}
		}
		}
		//Collections.reverse(gameSales);
		for (int i = 0; i < gameSales.length; i++){
			results += temp2[i];
			results += " = ";
			results += temp1[i];
			results += "\n";
			
			
		}
		JOptionPane.showMessageDialog(null, results);
		menu();
	}
	
	public static void totalGenreSales() throws IOException
	{
		int temp1[];
		String temp2[];
		int temp;
		String templ;
		boolean fixed =false;
		String gameGenres[] = new String[genre.size()];
		int gameSales[] = new int[genre.size()];
		int units;
		String results = "";
		for (int i = 0; i < gameGenres.length; i++)
			gameGenres[i] = genre.get(i).getGenreName();
		for (int i = 0; i < genre.size(); i++){
			int genreID = i+1;
			int totalUnits = 0;
			for (int k = 0; k < game.size(); k++){
				int gameID = k+1;
				int temp4 = game.get(k).getGenreID();
			for (int j = 0; j < sales.size(); j++){
				if (temp4 == genreID && sales.get(j).getGameID() == gameID){
					units = sales.get(j).getSaleUnits();
					totalUnits =totalUnits+ units;
				}
			}
				gameSales[i]=totalUnits;
			}
			
		}temp1 = gameSales;
		temp2 = gameGenres;
		while(fixed==false){
			fixed = true;
		
		for (int i=0;i<temp1.length-1;i++){
			if (temp1[i]<temp1[i+1]){
				temp = temp1[i+1];
				templ = temp2[i+1];
				temp1[i+1]=temp1[i];
				temp2[i+1]=temp2[i];
				temp1[i]=temp;
				temp2[i]=templ;
				fixed=false;
			}
		}
		}
		//Collections.reverse(gameSales);
		for (int i = 0; i < gameSales.length; i++){
			results += temp2[i];
			results += " = ";
			results += temp1[i];
			results += "\n";
			
			
		}
		JOptionPane.showMessageDialog(null, results);
		menu();
	}
	
}
	