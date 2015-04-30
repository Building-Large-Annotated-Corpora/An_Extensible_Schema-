Step 1: Compile and run the file 'XMLStripper.java' from within the directory '/code/preprocessing/' with the command:

	javac -cp ../../libraries/jsoup-1.8.2.jar:. XMLStripper.java

and run with

	java -cp ../../libraries/jsoup-1.8.2.jar:. XMLStripper

This will render the original XML formatted NYTimes documents in plain text devoid of mark-up formatting. 
Adjust the variable 'DIRECTORY_OF_NYTIMES_DATA' to change the location of the files to be processed. 


Step 2: 

A) In the directory '/code/preprocessing' run the script 'file_list.sh' and pipe the output to the '../../tools/file_list.txt' like so:


	./file_list.sh > ../../tools/file_list.txt


B) In the directory '/tools/' unzip Stanford CoreNLP.


C) Now process the files with the Stanford CoreNLP sentence splitter as follows:

	java -cp "*" -Xmx2g edu.stanford.nlp.pipeline.StanfordCoreNLP -annotators tokenize,ssplit -filelist ../file_list.txt -outputDirectory ../../input/bounded_sentences


Step 3: Now back in the directory '/code/preprocessing' compile the program 'SentenceBounds.java' with the command:

	javac -cp ../../libraries/jsoup-1.8.2.jar:. SentenceBounds.java

and run with the command:

    java -cp ../../libraries/jsoup-1.8.2.jar:. SentenceBounds

This will have rendered all the files that were previously cluttered by CoreNLP markup in a simplified format. ameanable to furthur processing in a comprehensible form, e.g.

    <sentence> 1 
    This was their first meeting since June 2004 -- not since the World Series in 1912 , the year they last played each other at Fenway Park . 
    </sentence>

Step 4: Now in the directory 'Building_Large_Annotated_Corpora' run the script 'named_entity_recognizer.sh'.

Step 5: In the directory 'code' compile the file 'Name_Grabber.java' with the command:

	javac -cp ../../libraries/jsoup-1.8.2.jar:. Name_Grabber.java

and run it with:

    java -cp ../../libraries/jsoup-1.8.2.jar:. Name_Grabber

Step 6: Now Compile the program 'Line_Slicer.java' with the command:

    javac Line_Slicer.java

and run as

    java Line_Slicer

Step 6: Now we are approaching the final step. In the directory 'labelling' run the command:

    javac -cp ../../libraries/jsoup-1.8.2.jar:../../libraries/commons-validator-1.4.1.jar:../../libraries/gson-2.2.4.jar:../../libraries/jackson-all-1.9.11.jar:../../libraries/commons-lang3-3.4.jar:../../libraries/commons-io-2.4.jar:. *.java

and subsequently:

    java -cp ../../libraries/jsoup-1.8.2.jar:../../libraries/commons-validator-1.4.1.jar:../../libraries/gson-2.2.4.jar:../../libraries/jackson-all-1.9.11.jar:../../libraries/commons-lang3-3.4.jar:../../libraries/commons-io-2.4.jar:. Runner

When this terminates run:

    java -cp ../../libraries/jsoup-1.8.2.jar:../../libraries/commons-validator-1.4.1.jar:../../libraries/gson-2.2.4.jar:../../libraries/jackson-all-1.9.11.jar:../../libraries/commons-lang3-3.4.jar:../../libraries/commons-io-2.4.jar:. Runner

To retrieve the Q values associated with all identified named entities, and finally:

    java -cp ../../libraries/jsoup-1.8.2.jar:../../libraries/commons-validator-1.4.1.jar:../../libraries/gson-2.2.4.jar:../../libraries/jackson-all-1.9.11.jar:../../libraries/commons-lang3-3.4.jar:../../libraries/commons-io-2.4.jar:. Label_Assigner

To determine the relationships between all named entities in the same sentence. 












