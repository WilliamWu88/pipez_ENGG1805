# pipez_ENGG1805
Development Time: April 2017
Project for ENGG1805 in The University of Sydney

# Introduction
Pipez is a simple system for executing workflows that do text transformations.
A workflow is defined as a pipeline of one or more pipes, each of which performs a specific transformation. 
Hence, we are able to perform a variety of text transformations by combining pipes into different combinations. 
The Pipez system takes an input of text, and using one or more pipes configured into a pipeline, creates a workflow that changes the input text to a new output format. 

Document support: Comma Separated File(CSV)

The Pipez software is made up of three main components:
- CSVReader
- Pipes
- CSVWriter

# CSVReader
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

# Pipes
A pipeline contains one or more pipes that transform the input Block into a new output Block.
The input Block is one line at a time specified as key-value pairs. That is, every field has an identifier (a key or field name) and a value. The pipe takes this input, performs the specified transformation, depending on the type of pipe it is, and writes the output as a Block with the same structure of key-value pairs.

For example, if the pipe used was the ReversePipe, which reverses the order of the fields the input and output would be as below:

(“C1”,”monkey”); (“C2”,”cat”); (“C3”, “baboon”)
-> (“C3”, “baboon”); (“C2”,”cat”); (“C1”,”monkey”)

If another pipe is used in the pipeline, the output Block from the ReversePipe pipe will become the input for the next pipe. So for example, if the next pipe used was OddFieldsPipe the situation would be as below:

(“C1”,”monkey”); (“C2”,”cat”); (“C3”, “baboon”)
-> (“C3”, “baboon”); (“C2”,”cat”); (“C1”,”monkey”)
-> (“C3”, “baboon”); (“C1”,”monkey”)


# Identity Pipe
This pipe passes the input field straight to the output without any transformation. This pipe takes no arguments.
Usage:
IdentityPipe.create()

# Oddfields Pipe
This pipe selects the odd numbered field of the input block. This pipe takes no arguments.
Usage:
OddFieldsPipe.create()

# Evenfields Pipe
This pipe selects the even numbered field of the input block only. This pipe takes no arguments.
Usage:
EvenFieldsPipe.create()

# Nfields Pipe
This pipe allows you to select specified numbered field from the input. Fields can be selected either from the beginning of the Block, using positive numbers, or from the end of the Block, using negative numbers.
This pipe takes the field numbers to be selected as arguments. The fields are selected in the order they are listed as arguments.

Usage:
In order to create an NFieldPipe that selects fields 3, 5 and 7, we would use:
NFieldPipe.create(3,5,7)

In order to create a NField pipe that selects the forth last and third last fields we would use:
NFieldPipe.create(-4,-3);

# Arithmetic Pipe
This pipe allows you to perform arithmetic operations on field values. That is, for any field you can either add, subtract, multiply or divide that field with another specified number.
The fields are identified by their keys (field names) and the pipe allows a different operation to be used for each field.

The pipe is created with arguments that give an operation, the field name or key (operand), and the data to be used in the operation.
Operations are:
- Addition specified using Add
- Subtraction specified using Minus
- Multiplication specified using Mult
- Division specified using Div

Usage:
ArithmeticPipe.create(Minus("C1", 1), Add("C2", 5), Div("C3", 6), Mult("C4", 2));
- subtracts 1 from the field with name (key) C1,
- adds 5 to the field with name (key)C2,
- divides the field with name (key)C3 by 6,
- and multiplies the field with name (key)C4 by 2

# RenameFields Pipe
This pipe changes the field name (which is the key in the block key-value pairs). That is, it searches for a field with a specified name, and renames it from that to a new name.
The pipe takes two arguments: a list of “from” names, and a list of “to” names. For each “from” name, the pipe will search for this field name and change it to the corresponding “to” name – this is done in the order they are supplied.
Note: The contents of the fields (their values) are not changed by this.

Usage:
To create a RenameFieldPipe that renames the fields as follows:
- renames field named “C1” to “Column A”,
- renames field named “C2” to “Column B”,
- renames field named “C3” to “Column C”.

RenameFieldsPipe.create(("C1","C2","C3"), ("Column A", "Column B", "Column C"));

# FieldMatch Pipe
This pipe returns the whole Block if the field names match the specified string under certain conditions. In its basic form, it is effectively a search for all Blocks which contain that field name in them. 
There are other forms which allow you to do the search under the conditions of “ignoring the case of the search string”, “selecting the inverse” i.e. blocks that do not contain that field name, or a combination of these conditions.
The pipe takes one argument, the search string.

Usage:
1. To create a FieldMatchPipe that looks for all Blocks that have a field named “Column A” we would use:
FieldsMatchPipe.create("Column A");

2. To create a FieldMatchPipe that looks for all Blocks that have a field named “Column A” where we
do not care about the case (e.g. it could match “column A” or “COLUMN A” etc), we would use:
FieldsMatchPipe.createIgnoreCase("Column A");

3. To create a FieldMatchPipe that looks for all Blocks that have do not have a field named “Column
A”, we would use:
FieldsMatchPipe.createInverse("Column A");

# ValueMatch Pipe
This pipe returns the whole Block if the field values match the specified string under certain conditions. In its basic form, it is effectively a search for all Blocks which contain that field value in them. 
There are other forms which allow you to do the search under the conditions of “ignoring the case of the search string”, “selecting the inverse” i.e. blocks that do not contain that field value, or a combination of these conditions.
The pipe takes one argument, the search string.

Usage:
1. To create a ValueMatchPipe that looks for all Blocks that have a field with the value “monkey”, we would use:
ValueMatchPipe.create("monkey");

2. To create a ValueMatchPipe that looks for all Blocks that have a field with the value “monkey” where we do not care about the case (e.g. it could match “MONKEY” or “MoNkEy” etc), we would use:
ValueMatchPipe.createIgnoreCase("monkey");

3. To create a ValueMatchPipe that looks for all Blocks that do not have a field with the
value “monkey”, we would use:
ValueMatchPipe.createInverse("monkey");

# CSVWriter
The CSV writer takes each output Block of the pipeline and stores it until the program explicitly requests a write, or it terminates successfully. 
Once this happens, the data in the Blocks is converted into a format where the field values are written separated by commas, with each Block being a new line. 
This writes all the final Blocks from the Pipeline as a CSV output file.


# Sample Usage of pipez system
1. Create a simple block

SimpleBlock sb = new SimpleBlock();

2. Adding data to the block

Method1: add() with field name and value
The first argument is the field name, and the second the field value. Each field is added separately in this case and the field name is explicitly stated.
If the field name already exists, its value will be overwritten.

sb.add("Col1", "ABC");
sb.add("Col2", "def");
sb.add("Col3", "GHI");

This creates a Block with values:
(“Col1”,”ABC”); (“Col2”,”def”); (“Col3”, “GHI”)

Method 2: add() with list of values
The add method can take a list of values (more than 2 arguments) that are the field values only. In this case, the field names are automatically generated and given the name “C”+field number. 
That is, if the block already has 3 fields and we add more fields using this method, the next fieldname will be generated as “C4”. Building on the example above, if AFTER we have used that code we add the line:
sb.add("monkey", "dog", "cat", "horse");

We would have the input Block as:
(“Col1”,”ABC”); (“Col2”,”def”); (“Col3”, “GHI”); (“C4”,”monkey”); (“C5”,”dog”); (“C6”,”cat”);
(“C7”,”horse”)

Method 3: Using the SimpleBlock constructor
Above is to specify the fields when creating the SimpleBlock. This will generate the field names “C”+field number starting from C1. The line:
SimpleBlock sb = new SimpleBlock("apple", "banana", "pear", "orange");
would generate an input Block:
(“C1”,”apple”); (“C2”,”banana”); (“C3”,”pear”); (“C4”,”orange”)

3. Use a pipe to transform the Block

Once the input Block has been defined, this is used as the input into the pipe being tested. To do this, a pipe is created, and this pipe is applied to the input Block.

SimpleBlock sb = new SimpleBlock(); //create empty SimpleBlock sb.add("C1", "banana"); //first column
sb.add("C2", "apple"); //second column
sb.add("C3", "pear"); //third column
ReversePipe reverse = ReversePipe.create(); //create a ReversePipe
Block output = reverse.transform(sb); //apply the pipe to the input Block
