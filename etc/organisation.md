# Work done
* GUI
    * Gameboard with tiles
    * Infos on current player
* Menu bar for options
* Game modes
    * 1 vs 1
    * Player vs AI
    * AI vs AI
* AI
    * two eval0 methods
    
# Implementation difficulties
* Once AI was implemented, make it play as another human player
was hard: too fast, not the same way to choose its move, etc.. we had
to rework the whole project to make it work.

* Successors was complicated to generate too: they were many cases to handle 
(each row, each cell, each direction). We had to check whether a successor was
already defined or not, regenerate them after each game state change, etc. and 
everything while respecting the game's rules.


# Evolutions
* Evolutive AI was something we didn't successfully implemented,
rework this part could be a good evolution.

* More eval0 functions to allow the user to chose between several
level of difficulties when playing against an AI.

# Organisation
## Day 1
* Repositiory initialization
* Rules definitions
* Main class

* lhome1u
    * ReversiPlayer
    * Player
* pBouillon
    * ReversiGameState
    * GameState
    * GameColor
    
## Day 1 - off
* lhome1u
    * Successors generation
    * swapping pieces
* pBouillon
    * Empty play functions
    * Grid terminal display from game states
    * GUI
        * Menu
        * Game  
    * Started GameState handling in game
    * Successors generation
    * Win check
    
## Day 2
* lhome1u
    * Patched swapping
    * GUI
        * Rules
* pBouillon
    * GUI
        * End Game
        * Game: achieving 1 vs 1 (human players)
    * Patched `isWon()`
    * Started `eval_c(int: lvl, GameState: state)`

## Day 3
* lhome1u and pBouillon
    * `minimax`, `eval0`, `evalc`, `eval alpha/beta` implementation

## Day 3 - off
* pBouillon
    * Started sources reorganization

## Day4
* lhome1u and pBouillon
    * Rewritting sources
    * GUI patchs (still in progress..)

## Day 4 - off
* lhome1u
    * reworked model
    * reworked view (switched to MVC)

## Day 5
* pBouillon
    * reworked GUI
    * changed images

## Day 5 - off
* pBouillon
    * patched eval_0, eval of a final state
    * writing eval_0_v2 
    * added successor's indicator
    * finished stats view
    * depth change for AI search
    * grid size change