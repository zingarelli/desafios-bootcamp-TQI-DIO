const numberOfCards = 20; //how many cards this memory game has
const cards = document.querySelectorAll('.card');
const newGame = document.getElementsByTagName('button')[0];
let hasFlippedCard = false; //controls if the first card has been flipped or not
let firstCard, secondCard; //cards flipped by the player
let lockBoard = false; //prevents the player from flipping more than 2 cards
let numberOfAttempts = 0; //number of attempts to match cards in the current game
let numberOfMatches = 0; //how many cards got a match in the current game
let bestRound = Number.MAX_VALUE; //minimum number of attempts of all (until page is refreshed)

/* 
    This function will be invoked whenever a player clicks on a card.
    Adds a "flip" class to the card clicked, signaling it was flipped.
    Once two cards have been flipped, check if the player got a match or not.
*/
function flipCard() {
    if(lockBoard) return; //two cards have already been flipped, so no more cards can be flipped
    if(this === firstCard) return; //prevents the player from flipping the same card again

    this.classList.add('flip');
    
    if(!hasFlippedCard) { //it's the first card flipped by the player
        hasFlippedCard = true;
        firstCard = this;
        return;
    }

    // it's the second card flipped by the player, time to check if the player got a match.
    secondCard = this;
    checkForMatch();
}

//check if two flipped cards are the same
function checkForMatch() {
    updateAttempts();
    lockBoard = true; //board will be locked so no more cards will be flipped until this round is resolved

    /* custom data attributes on HTML elements (data-card in this case)
       check: https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/dataset  */    
    //match
    if(firstCard.dataset.card === secondCard.dataset.card) {
        disableCards(); // prevent player from flipping the cards again
        numberOfMatches++;  
        checkEndGame();
        return; //this round is over
    }

    unflipCards();
}

//check if all card got a match and update best score if needed
function checkEndGame(){
    //each match is a pair, so the maximum of matches is equal to half of the total number of cards
    if(numberOfMatches === (numberOfCards / 2)){
        updateBestScore();
    }
}

//check if the current round has the best score and updates the board
function updateBestScore(){
    if (numberOfAttempts < bestRound) {
        bestRound = numberOfAttempts;
        document.getElementById('best-score').innerHTML = bestRound + ' ATTEMPTS'
        highScoreText(true);
    }
}

//show/hide High Score Text
function highScoreText(show){
    let highScore = document.getElementById('new-high-score');
    show ? highScore.style.visibility = 'visible' : highScore.style.visibility = 'hidden';
}

//show how many cards were flipped in the current game
function updateAttempts(reset = false) {
    reset ? numberOfAttempts = 0: numberOfAttempts++; //if it's a new game, counter is reset to zero
    document.getElementById('current-score').innerHTML = numberOfAttempts + ' ATTEMPTS';
}

//remove the listener from the cards that got a match and free the board for another round
function disableCards() {
    firstCard.removeEventListener('click', flipCard);
    secondCard.removeEventListener('click', flipCard);

    resetBoard();
}

//remove "flip" class from the cards that didn't get a match
function unflipCards() {
    //we'll reveal the cards for 1.5s before unflipping them
    setTimeout(() => {
        firstCard.classList.remove('flip');
        secondCard.classList.remove('flip');

        resetBoard(); //board is free for another round
    }, 1800);
}

//reset all control variables for another round
function resetBoard() {
    [hasFlippedCard, lockBoard] = [false, false]; // this is array destructuring
    [firstCard, secondCard] = [null, null];
}

//Randomly shuffle the position of all cards when the page loads
function shuffle() {
    cards.forEach((card) => {
        let randomPosition = Math.floor(Math.random() * numberOfCards);
        card.style.order = randomPosition; //cards with the same position will be aligned in the order of appearance in the HTML tree
    })
};

//Flip all cards for a few seconds, then unflip them
function showCards(){
    cards.forEach((card) => {
        card.classList.add('flip');
    });
    setTimeout(() => {
        cards.forEach((card) => {
            card.classList.remove('flip');
        });
        //cards and new game button will be clickable only after cards are shown
        //TODO: solve this with Promise?
        cardEvent(true);
        newGameEvent(true);
    }, 4000);
};

/* 
    This function creates a new game. 
    Cards and new game button won't do anything if clicked, to prevent the player from clicking until everything is loaded.
    Control variables of the board and number of attempts and matches will be reset.
    Cards will be shuffled then shown for a few seconds.
*/
function loadBoard(){
    cardEvent(false);
    newGameEvent(false);
    highScoreText(false);
    updateAttempts(true);
    numberOfMatches = 0;
    resetBoard();
    shuffle();
    showCards();    
}

// toggle cards of being able to flip
function cardEvent(enabled) {
    if(enabled){
        cards.forEach((card) => {
            card.addEventListener('click', flipCard)
        });
    }
    else{
        cards.forEach((card) => {
            card.removeEventListener('click', flipCard)
        });
    }
}

// toggle the new game button of being able to reset the board
function newGameEvent(enabled) {
    if(enabled){
        newGame.addEventListener('click', loadBoard);
    }
    else{
        newGame.removeEventListener('click', loadBoard);
    }
}

//IIFE. Load the board whenever the page is opened/refreshed
(loadBoard)();


/* TODO
- add a scoreboard (left panel) with:
-- number of attempts of current game -- DONE
-- best score (less number of attempts to complete a game) -- DONE
-- new game button -- DONE
-- logic to show number of attempts and reset when it's a new game -- DONE
-- logic to show/update best score -- DONE
- Show some congratulations panel then reload the board once a game is completed;
-- prompt the player to input his/her name when reaching a new high score; then add it to the side panel.
*/