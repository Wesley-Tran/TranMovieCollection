import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        //title, cast, director, tagline, keywords, overview, runtime, genres, userRating, Year, Revenue
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        // keywords, overview, runtime (int), genres,
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        // user rating (double), year (int), and revenue (int)
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        // create a Movie object with the row data:
        Movie newMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(newMovie);

      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortString(ArrayList<String> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      String temp = listToSort.get(j);

      int possibleIndex = j; //used to add it to the end and to keep track of what we're checking
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search term: ");
    String keyword = scanner.nextLine();

    keyword = keyword.toLowerCase();

    ArrayList<Movie> results = new ArrayList<>();

    for (int i= 0; i < movies.size(); i++) {
      String movieKeywords = movies.get(i).getKeywords().toLowerCase();
      if (movieKeywords.indexOf(keyword) >= 0) {
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() { //
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();

    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> allCast = new ArrayList<>(); // all of the cast members
    for (Movie movie : movies) {
      String cast = movie.getCast();
      String[] castList = cast.split("\\|");
      for (String name : castList) {
        if (!allCast.contains(name)) { //adds the name of the cast members to allCast IF they aren't in there
          allCast.add(name);
        }
      }
    }

    ArrayList<String> searchedCast = new ArrayList<>(); // cast members that have that name
    for (String i : allCast) {
      if (i.toLowerCase().contains(searchTerm)) {
        searchedCast.add(i);
      }
    }

    if (searchedCast.size() > 0) {
      // sort the results by cast name
      sortString(searchedCast);

      // now, display them all to the user
      for (int i = 0; i < searchedCast.size(); i++) {
        String castName = searchedCast.get(i);

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + castName);
      }

      System.out.println("Which would you like to see all movies for?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      String selectedName = searchedCast.get(choice - 1); //the name of the cast member

      // amke a new list (empty)
      ArrayList<Movie> memberIncluded = new ArrayList<>();
      // search through the movies and if the name of the cast is in the cast, add to the list
      for (Movie i : movies) {
        if (i.getCast().contains(selectedName)) {
          memberIncluded.add(i);
        }
      }
      // display movies and that the cast member is in and ask which movie to learn about
      if (memberIncluded.size() > 0) {
        // sort the results by title
        sortResults(memberIncluded);

        // now, display them all to the user
        for (int i = 0; i < memberIncluded.size(); i++) {
          String title = memberIncluded.get(i).getTitle();

          // this will print index 0 as choice 1 in the results list; better for user!
          int choiceNum = i + 1;
          System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = memberIncluded.get(userChoice - 1);
        displayMovieInfo(selectedMovie);
      }
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movies star that cast member!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }


  }
  
  private void listGenres() {
    ArrayList<String> genres = new ArrayList<>();
    for (Movie i : movies) { //get list of genres
      String[] listOfGenres = i.getGenres().split("\\|");
      for (String j : listOfGenres) {
        if (!genres.contains(j)) {
          genres.add(j);
        }
      }
    }

    if (genres.size() > 0) { // displays genres to choose from
      sortString(genres);

      // now, display them all to the user
      for (int i = 0; i < genres.size(); i++) {
        String genre = genres.get(i);

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + genre);
      }

      System.out.println("Which would you like to see all movies for?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      String selectedGenre = genres.get(choice - 1);

      ArrayList<Movie>  moviesFiltered = new ArrayList<>();
      for (Movie i : movies) {
        if (i.getGenres().toLowerCase().contains(selectedGenre.toLowerCase())) {
          moviesFiltered.add(i);
        }
      }

      if (moviesFiltered.size() > 0) {
        // sort the results by title
        sortResults(moviesFiltered);

        // now, display them all to the user
        for (int i = 0; i < moviesFiltered.size(); i++) {
          String title = moviesFiltered.get(i).getTitle();

          // this will print index 0 as choice 1 in the results list; better for user!
          int choiceNum = i + 1;
          System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesFiltered.get(movieChoice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
      } else {
        System.out.println("\nNo movie titles match that search term!");
        System.out.println("** Press Enter to Return to Main Menu **");
        scanner.nextLine();
      }

    }

  }
  
  private void listHighestRated() {
    ArrayList<Movie> highestRated = new ArrayList<>();
    highestRated.add(movies.get(0));
    for (int i = 1; i < movies.size(); i++) {
      for (int j = 0; j < highestRated.size(); j++) {
        if (highestRated.get(j).getUserRating() < movies.get(i).getUserRating()) { //compare movies in the rankings to current movie
          highestRated.add(j,movies.get(i));
          break;
        }
      }
      if (highestRated.size() == 51) { //dont go over 50 members
        highestRated.remove(50);
      }
    }
    for (int i = 0; i < highestRated.size(); i++) {
      String title = highestRated.get(i).getTitle();
      double rating = highestRated.get(i).getUserRating();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + title + ": " + rating);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int userChoice = scanner.nextInt();
    scanner.nextLine();
    Movie selectedMovie = highestRated.get(userChoice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRevenue() {
    ArrayList<Movie> highestRevenue = new ArrayList<>();
    highestRevenue.add(movies.get(0));
    for (int i = 1; i < movies.size(); i++) {
      for (int j = 0; j < highestRevenue.size(); j++) {
        if (highestRevenue.get(j).getRevenue() < movies.get(i).getRevenue()) { //compare movies' revenue to current movie's revenue
          highestRevenue.add(j,movies.get(i));
          break;
        } else {
          highestRevenue.add(movies.get(i));
          break;
        }
      }
      if (highestRevenue.size() == 51) { //dont go over 50 moveis
        highestRevenue.remove(50);
      }
    }
    for (int i = 0; i < highestRevenue.size(); i++) {
      String title = highestRevenue.get(i).getTitle();
      int revenue = highestRevenue.get(i).getRevenue();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + title + ": " + revenue);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int userChoice = scanner.nextInt();
    scanner.nextLine();
    Movie selectedMovie = highestRevenue.get(userChoice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
}