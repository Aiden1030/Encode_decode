package assignment2;

import java.util.Random;

public class Deck {




 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck


  /*
  * TODO: Initializes a Deck object using the inputs provided
  */
 public Deck(int numOfCardsPerSuit, int numOfSuits) {

  this.numOfCards = numOfCardsPerSuit*numOfSuits+2;

  if ( 1  > numOfCardsPerSuit || 13 < numOfCardsPerSuit  || (numOfSuits > suitsInOrder.length) || (numOfSuits < 1 )){

            throw new IllegalArgumentException();
            }

  Card deckTail = this.head;
  //Suits for loop
  for(int num = 0; numOfSuits > num; num++) {

          String suit = suitsInOrder[num];
   // number for loop
          for (int suitNumber = 0; numOfCardsPerSuit > suitNumber; suitNumber++) {

                 PlayingCard newCard = new PlayingCard(suit, suitNumber + 1);

          if (num == 0 && suitNumber == 0) {
                this.head = newCard;}
          else{
               linkCard(deckTail, newCard);}

          deckTail = newCard;

          }

  }

  Joker redJoker = new Joker("red");

  Joker blackJoker = new Joker("black");

 linkCard(deckTail,redJoker);

 linkCard(redJoker,blackJoker);

 linkCard(blackJoker,this.head);

  }

 private void linkCard(Card cardA, Card cardB){

  cardA.next = cardB;

  cardB.prev = cardA;

 }
 /* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {

     if (d == null || d.head == null || d.numOfCards == 0){

     }else {

         this.numOfCards = d.numOfCards;

         this.head = d.head.getCopy();

         Card fakeTail = this.head;

         Card originalTail = d.head;

         Card originalCard = d.head.next;
         Card copy = d.head;

         while (1 == 1) {


             if (originalCard == d.head) {

                 linkCard(fakeTail, this.head);

                 break;
             }

             copy = originalCard.getCopy();

             linkCard(fakeTail, copy);


             fakeTail = copy;

             originalCard = originalCard.next;
         }

     }

 }

 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {
     if (this.head == null){
         this.numOfCards =1;
         this.head = c;
         this.head.prev = c;
                 this.head.next = c;
     }else{

  this.numOfCards +=1;}
  linkCard(this.head.prev,c);
  linkCard(c,this.head);
 }



 //}
 /*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */



 private Card[] cardArrayDeck (Deck deck){

  Card thisCard = deck.head;

  int num = deck.numOfCards;

  Card[] result = new Card[deck.numOfCards];

 for (int i=0; i < num; i++){

      result[i]= thisCard;

      thisCard = thisCard.next;

 }return result;

 }
 public void shuffle() {

  int num = this.numOfCards;

  Card[] cardArray = cardArrayDeck(this);



  for (int t=this.numOfCards-1; t>0;t--){

   int randomNumber = gen.nextInt(t+1);

         Card randomCard = cardArray[randomNumber];

         cardArray[randomNumber] = cardArray[t];

         cardArray[t] = randomCard;

  }


  for (Card card: cardArray){

          this.head = cardArray[0];
          //System.out.println("numof card  "+ Integer.valueOf(this.numOfCards));

          for (int i=0; i< this.numOfCards; i++){

                    if (i== this.numOfCards-1){

                    linkCard(cardArray[i] ,cardArray[0]);
                    }else{

                     linkCard(cardArray[i],cardArray[i+1]);}
          }
  }

 }





 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {


  Card head = this.head;

  for (int i=0; i<this.numOfCards;i++){

       if (head instanceof Joker){

            if (color.toLowerCase() == ((Joker) head).redOrBlack){

                return (Deck.Joker) head;
            }
       }
             head = head.next;
  }

  return null;

 }



 private void deLink(Card card){

     Card prev = card.prev;
     Card next = card.next;

     prev.next = next;
     next.prev = prev;

     card.next = null;
     card.prev = null;


 }

 private void insert( Card prev,Card c, Card next){

     prev.next = c;
     next.prev = c;
     c.next = next;
     c.prev = prev;

 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */

    public void moveCard(Card c, int p){

        if (p % (this.numOfCards-1) == 0){return;}



        Card next = c.next;
        Card prev = c.prev;
        Card cardA = c;

        for (int i=0; i < p ;++i){

            cardA = cardA.next;

        }
        Card cardB = cardA.next;

        if(this.numOfCards<4){

            insert(cardA,c,cardB);
            linkCard(cardB,cardA);

        }

        deLink(c);

        insert( cardA, c, cardB);

    }



 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */



 public void tripleCut(Card firstCard, Card secondCard) {


  Card nextTail;

  Card nextHead;

  Card tail;


     if(firstCard == this.head && secondCard == this.head.prev){


         return;







     }
     else if(firstCard == this.head){
         tail = firstCard.prev;

         nextHead = secondCard.next;

         nextTail = secondCard;

         linkCard(tail,firstCard);

         this.head = nextHead;
     }
     else if(secondCard == this.head.prev){

         tail = secondCard;

         nextHead = firstCard;

         nextTail = firstCard.prev;

         linkCard(secondCard,this.head);

         linkCard(nextTail,firstCard);

         this.head = nextHead;
     }
     else {

          nextTail = firstCard.prev;

          nextHead = secondCard.next;

          tail = (this.head).prev;

         linkCard(tail,firstCard);

         linkCard(secondCard, this.head);

         linkCard(nextTail, nextHead);

         this.head = nextHead;
     }




 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {

     if(this.head == null){return;}

  Card tail = this.head.prev;

  Card tailPrev = tail.prev;

  int tailValue = tail.getValue()%this.numOfCards;

  Card cutTail = this.head;

  for (int i=0;i<tailValue-1;i++){
        cutTail = cutTail.next;

  }



  if ((tailValue == (this.numOfCards-1))|| tailValue == 0){
      return;}
  else{

        Card thisHead = cutTail.next;

        linkCard(tail,thisHead);

        linkCard(cutTail,tail);

        linkCard(tailPrev,this.head);

        this.head = thisHead;}

 }






 /*
  * TODO: Returns the card that can be found by looking at the value of the 
  * card on the top of the deck, and counting down that many cards. If the 
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {

  int headValue = this.head.getValue();

  Card card = this.head;

  for (int i=0; i<headValue; i++){

         card = card.next;
  }


  if (card instanceof Joker){


        return null;

  }else{

         return card;
  }


 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */

 private int locateCard(Card c){
//0
  Card card = this.head;
//
  for (int i = 0 ; i < this.numOfCards; i++){

       if ( card == c ){

           return i;

       }else{

             card = card.next;

       }
  }

  return -1;
 }

 public int generateNextKeystreamValue() {


int B = 0;


     while(B==B){ B = B+1;

         int RJlocation = -1;
         int BJlocation = -1;

  Card firstcard = new PlayingCard("A",1);

  Card secondcard = new PlayingCard("A",1);

  Card redJoker = locateJoker("red");



  if (redJoker == null){RJlocation = -2;}else{

         moveCard(redJoker,1);

  }



  Card blackJoker = locateJoker("black");

  if (blackJoker == null) { BJlocation = -2;}else {


      moveCard(blackJoker, 2);


  }
if (RJlocation == -1 ) {
             RJlocation = locateCard(redJoker);
         }
if(BJlocation == -1){
    BJlocation = locateCard(blackJoker);
}



   if (RJlocation > BJlocation){

      firstcard = blackJoker;

      secondcard = redJoker;

  }else if (BJlocation > RJlocation){

      firstcard = redJoker;

      secondcard = blackJoker;

  }else {}

       tripleCut(firstcard, secondcard);




       countCut();



  Card lookedUpCard = lookUpCard();



  if(lookedUpCard == null){





  }else{



   return lookedUpCard.getValue();

  }}
return 0;
 }


 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
