import java.util.ArrayList;

public class Spoons {
    ArrayList<Integer> deck;
    SpoonPlayer[] spoonPlayers;
    public void initDeck(){
        deck = new ArrayList<Integer>();
        ArrayList<Integer> deckTwo = new ArrayList<Integer>();
        for(int i = 0; i < 52; i++){
            deckTwo.add(i+1);
        }
        for(int i = 0; i < 52; i++){
            deck.add(deckTwo.remove((int)(Math.random()* deckTwo.size())));
        }
    }
    public void initGame(){
        initDeck();
        for (int i = 0; i < spoonPlayers.length; i++){
            int[] hand = new int[4];
            for(int j = 0; j < 4; j++){
                hand[j] = deck.remove((int)(Math.random()* deck.size()));
            }
            spoonPlayers[i].setHand(hand);
        }

    }
    public void eachOneTurn(){
        int card = deck.remove(0);
        for(SpoonPlayer player: spoonPlayers){
            card = player.play(card);
            if(player.won()){
                return;
            }
        }
        deck.add(card);
    }
    public Spoons(SpoonPlayer[] spoonPlayers){
        this.spoonPlayers = spoonPlayers;
    }

    public boolean isWon(){
        for(SpoonPlayer player: spoonPlayers){
            if(player.won()){
                return true;
            }
        }
        return false;
    }
    public int whoWon(){
        int c =0;
        for(SpoonPlayer player: spoonPlayers){
            if(player.won()){
                return c;
            }
            c++;
        }
        return -1;
    }

    public static void main(String[] args) {
        SpoonPlayer max = new SpoonPlayer(true);
        SpoonPlayer tiago = new SpoonPlayer(false);
        SpoonPlayer chloe = new SpoonPlayer(false);
        SpoonPlayer luci = new SpoonPlayer(true);
        SpoonPlayer sam = new SpoonPlayer(false);
        SpoonPlayer lucia = new SpoonPlayer(false);
        SpoonPlayer person = new SpoonPlayer(true);
        SpoonPlayer person2 = new SpoonPlayer(false);
        SpoonPlayer person3 = new SpoonPlayer(false);
        SpoonPlayer person4 = new SpoonPlayer(false);
        SpoonPlayer person5 = new SpoonPlayer(false);
        SpoonPlayer person6 = new SpoonPlayer(false);
        SpoonPlayer person7 = new SpoonPlayer(true);

        SpoonPlayer[] peeps = {max, chloe, luci, sam, lucia, person, person2, person3, person4, person5, tiago};
        int[] wins = new int[peeps.length];
        Spoons game = new Spoons(peeps);
	    for(int i = 0; i < 100; i++) {
            game.initGame();
            while (!game.isWon()) {
                game.eachOneTurn();
            }
            System.out.println(peeps[game.whoWon()]);
            wins[game.whoWon()]++;
        }
	    int total = 0;
        for (int win : wins) {
            total += win;

        }
        for(int win: wins){
            System.out.println(win + " " + ((double)win/total)*100 + "%");
        }
    }
}
