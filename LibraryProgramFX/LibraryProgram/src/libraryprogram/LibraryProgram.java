package libraryprogram;

public class LibraryProgram {
    
    public static void main(String[] args) { 
        Games games = new Games();
        //System.out.println(games.getGame(0).storeString());
        System.out.println(games.getNumGames());
        
        games.sortGames();
        
        System.out.println("-------------");
        System.out.println(games.getNumGames());
        
//        Platforms platforms = new Platforms();
//        System.out.println(platforms.getPlatform(0).storeString());
    }

}
