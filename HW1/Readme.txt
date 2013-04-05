1. Instructions on how to compile and execute the code.
	1. Using "javac *.java" command to compile all the source code.
	2. Using "java Main" command to execute the main method of the program.
	3. The java version on my account in aludra is "java version "1.5.0_18".
		So, please make sure the java version is correct.
	
2. Instructions on how to select the search algorithm in user interface.
	In the prompted line will indicate you "Please select the algorithms from 
	BFS, DFS, UCS." Then input one of the three strings: "BFS", "DFS", "UCS".
	Or, you can also input "PRINT" to print the graph and "QUIT" to quit the 
	program directly.
	
	If you want to test other search algorithms, please just run "java Main" again.
	
3. Brief description of the program structure
	The program contains 7 java classes. As the class name indicated, they have
	different functions. I used Stack from java's built-in class. But derived the
	Queue by myself.
	
	The 3 search algorithms are defined in Search class.
	
4. Other issue.
	The program cannot let user to define the start node and end node. But this 
	function is not hard.