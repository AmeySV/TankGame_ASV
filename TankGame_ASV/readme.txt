IDE used: IntelliJ IDEA Ultimate 2019.3
Java version: 15
Working Directory: Wherever you place the .jar and layout1.txt files (more info in instructions)

Instructions to run the game: Place the .jar and layout1.txt files (currently in the jar folder) in whichever directory you want (as long as you can access that directory through Command Prompt/Terminal etc.).
                              Once in the directory of your choice, open your command-line, navigate to that directory, and enter the command:
                              java -jar TankGame_ASV.jar
                              NOTE: layout1.txt must be placed in your working directory; the game will not function otherwise.

Controls: WASD and Up/Down/Left/Right for P1 and P2 movement respectively (WS and Up/Down move players forward and backwards, AD and Left/Right turn players around).
          Space and Enter for P1 and P2 shooting.

IMPORTANT: layout1.txt determines the layout of the arena for the game; however, layout1 is customizable by the end-user.
           The arena is divided into a layout of 24 long by 12 tall squares; this is displayed in layout1.txt with 12 lines of 24 characters.
           The only characters of importance to the game are B, H, R, U, 1 and 2. All other characters are interpreted as empty space (in the case of the original, "~" is used).
	   B denotes breakable walls, while U denotes unbreakable walls. H is where health packs spawn, while R is for bullet upgrades.
	   Finally, 1 and 2 denote the starting spawn locations for P1 and P2, respectively (respawn locations are randomized based on empty space around the starting spawn).
           You can freely change this file using the above guidelines to create your arena of choice, if you don't happen to like the schematic used here,
	   as long as you don't go past the 24x12 restriction.
           
