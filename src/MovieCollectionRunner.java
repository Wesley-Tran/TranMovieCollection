public class MovieCollectionRunner {
  public static void main(String[] args) {
    String csvfile = "src\\movies_data.csv";

    MovieCollection movieCollection = new MovieCollection(csvfile);
    movieCollection.menu();




  }
}