This program uses a word list to compute solutions to changelings in three letter words.

A **Changeling** in some crossword puzzle books is a pair of words with the same number of letters. To solve the changeling, there must be a sequence of words with each differing from the previous by a single letter. This transforms the first word of the changeling into the second.

For example, one solution to the changeling **CAT** to **DOG** is:

- CAT
- COT
- DOT
- DOG

The intermediate words must also be words.

This program computes a solution to a changeling from the user inputted `firstword` to the user inputted `secondword`, and both must be chosen from the given text file `enable3letter.txt`. 

In the command prompt, the input must look like so:
`java Changeling enable3letter.txt firstword secondword`.
