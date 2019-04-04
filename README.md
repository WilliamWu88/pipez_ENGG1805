# pipez_ENGG1805
pipez, developed in ENGG1805 in 2017 April.

Pipez is a simple system for executing workflows that do text transformations.
A workflow is defined as a pipeline of one or more pipes, each of which performs a specific transformation. 
Hence, we are able to perform a variety of text transformations by combining pipes into different combinations. 
The Pipez system takes an input of text, and using one or more pipes configured into a pipeline, creates a workflow that changes the input text to a new output format. 

Document support: Comma Separated File(CSV)

The Pipez software is made up of three main components:
- CSVReader
- Pipes
- CSVWriter

The input CSV file is a comma-separated file made up of one or more lines, and each line can have any number of fields in them. 
The CSV file is stored in a directory on the computer and the Pipez software can open the file and read its contents using the CSVReader component of the code.
The CSVReader reads each line of the input file and creates a Block from this one line of data using the value it reads from each field and gives each field an identifier (or key/field name). So, a Block is effectively an array that contains the data of one line of the input file.

For example, if example.csv is like this:
apple, pear, orange, banana
monkey, cat, baboon
125, 645, 753, 459

The first Block that the CSVReader will output will have four values: (“C1”,”apple”); (“C2”,”pear”); (“C3”, “orange”); (“C4”,”banana”)
“C1”, “C2”, “C3” and “C4” are the keys or field names, and “apple”, “pear”, “orange”, and “banana” are the field values.

The next Block will have three values:
(“C1”,”monkey”); (“C2”,”cat”); (“C3”, “baboon”)

The third Block generate will have four values:
(“C1”,”125”); (“C2”,”645”); (“C3”, “753”); (“C4”,”459”)

The lines of the CSV file are read one at a time and the resulting Block is the input to the Pipeline.
