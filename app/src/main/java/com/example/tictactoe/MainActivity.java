  package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/*      this is our guide 2-dimensional array pair
        [0][0]    [0][1]    [0][2]
        [1][0]    [1][1]    [1][2]
        [2][0]    [2][1]    [2][2]
        this array pair is actually the logical representation of the buttons
 */


  public class MainActivity extends AppCompatActivity implements View.OnClickListener {

      //      buttons to set arrays for two scrap record pairs and fill 3 as in the number of array sets
      private Button[][] buttons = new Button[3][3];

      private boolean player1Turn = true;

      //    keeps count of the number of rounds gone in the game; max = 9
      private int roundCount;

      //      calculates the point of each player
      private int player1Points;
      private int player2Points;

      //      displays the point of each player
      private TextView tvPlayer1;
      private TextView tvPlayer2;


      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          tvPlayer1 = findViewById(R.id.text_view_p1);
          tvPlayer2 = findViewById(R.id.text_view_p2);

/*        create reference to all our array buttons at once using the for loop
            i and j represent the rows and columns of the 9 buttons
            the for loop sets the count of each button to 0<x<3 and then increments them for each round
 */
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  String buttonID = "button_" + i + j;
//                the below statement fetches the resource library used to dynamically refer to the buttons
                  int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                  buttons[i][j] = findViewById(resID);
                  buttons[i][j].setOnClickListener(this);
              }
          }

          Button buttonReset = findViewById(R.id.reset_button);

          buttonReset.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                  resetGame();
              }
          });
          }

      @Override
      public void onClick(View v) {
//          if statement checks if there is a character ie 'X or O' on any button
          if (!((Button) v).getText().toString().equals("")) {
              return;
          }

//          if statement checks if it is player one's turn and if it is, it writes x when a button is clicked
          if (player1Turn) {
              ((Button) v).setText("X");
          }
//          else if it is player two's turn, it writes o when a button is clicked
          else {
              ((Button) v).setText("O");
          }

//          increases the round count of the game session
          roundCount++;

          if (checkForWin()){
//              checks if the checkForWin method is satisfied by the player one's turn and calls the player1Wins method
              if (player1Turn){
                  player1Wins();
              }else{
//                  if the checkForWin method was not satisfied by the player one's turn ie it was by player two, the player2Wins method is called
                  player2Wins();
              }
//              checks if the total round count is exhausted and thus calls the draw method
          }else if (roundCount == 9) {
              draw();
          }else {
//              if there is no winner and there is no draw ie the game is still on, it switches turns by setting player1Turn (which is true) to its opposite(which is false, which is player two's turn)
              player1Turn = !player1Turn;
          }

      }

//      checks for a win after each round
      private boolean checkForWin() {
          String[][] field = new String[3][3];

          for (int i = 0; i < 3; i++){
              for (int j = 0; j < 3; j++) {
                  field[i][j] = buttons[i][j].getText().toString();
              }
          }

//          The following two for statements check the vertical and horizontal line buttons for conformity
/*          the for statement checks the contents of each button on each row whether they are the same
            it also checks to confirm that the corresponding rows are not empty, in  order to select a winner
 */
          for (int i = 0; i < 3; i++) {
              if (field[i][0].equals(field[i][1])
              && field[i][0].equals(field[i][2])
              && !field[i][0].equals("")){
                  return true;
              }
          }

/*          this for statement checks the content of each button on each columns whether they are the same
            it also checks to confirm that the corresponding rows are not empty, in order to select a winner
 */

              for (int i = 0 ; i < 3; i++) {
                  if (field[0][i].equals(field[1][i])
                          && field[0][i].equals(field[2][i])
                          && !field[0][i].equals("")){
                      return true;
                  }

              }

//              the following two if statements check the content of each button on each diagonal axis for equality
          if (field[0][0].equals(field[1][1])
                  && field[0][0].equals(field[2][2])
                  && !field[0][0].equals("")){
              return true;
          }

          if (field[0][2].equals(field[1][1])
                  && field[0][2].equals(field[2][0])
                  && !field[0][2].equals("")){
              return true;
          }

          return false;
      }

      private void player1Wins(){
//          increments the player one's point count
            player1Points++;
//            makes a toast
          Toast.makeText(this,"Player 1 wins!",Toast.LENGTH_LONG).show();
//          calls the updatePointsText method
          updatePointsText();
//          calls the resetBoard method
          resetBoard();
      }

      private void player2Wins(){
//          increments the player two's point count
          player2Points++;
//            makes a toast
          Toast.makeText(this,"Player 2 wins!",Toast.LENGTH_LONG).show();
//          calls the updatePointsText method
          updatePointsText();
//          calls the resetBoard method
          resetBoard();
      }

      private void draw(){
            Toast.makeText(this,"Draw!",Toast.LENGTH_LONG).show();
            resetBoard();
      }

      private void updatePointsText(){
          tvPlayer1.setText("Player 1: " +player1Points);
          tvPlayer2.setText("Player 2: " +player2Points);
      }

      private void resetBoard(){
//          the for loop goes through each and every one of our buttons as defined in the dynamic reference and sets their contents to empty strings
          for (int i = 0; i<3; i++){
              for (int j = 0; j < 3; j++){
                  buttons[i][j].setText("");
              }
          }

          roundCount = 0;
          player1Turn = true;
      }

      private void resetGame(){
          player1Points = 0;
          player2Points = 0;
//          calls the updatePointsText method when the reset button is clicked
          updatePointsText();
//          calls the resetBoard method when the reset button is clicked
          resetBoard();
      }

//      Method to save the data, text and logic states of the game when the orientation is changed
      @Override
      protected void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);

//          sets values of the various variables in key - value mode
          outState.putInt("roundCount", roundCount);
          outState.putInt("player1Point", player1Points);
          outState.putInt("player2Point", player2Points);
          outState.putBoolean("player1Turn", player1Turn);
      }

//      Method to restore the data, text and logic states of the game when the orientation is changed
      @Override
      protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
          super.onRestoreInstanceState(savedInstanceState);

//          restores values of the various variables using their saved keys in the onSaveInstanceState method
          roundCount = savedInstanceState.getInt("roundCount");
          player1Points = savedInstanceState.getInt("player1Point");
          player2Points = savedInstanceState.getInt("player2Point");
          player1Turn = savedInstanceState.getBoolean("player1Turn");
      }
  }

