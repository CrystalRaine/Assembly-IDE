# Assembly-Info
an IDE for LEGv8 Assembly

Leg assembly is a simplified subset of ARMv8 assembly, specifically designed for teaching purposes. 

A greensheet for the language can be found here: https://montcs.bloomu.edu/Information/ARMv8/legv8-green-card.compressed.pdf

Currently, as the project is a work in progress, not all instructions are implemented. Fully Implemented instructions and usage are can be found in Info/Commands.txt, or displayed in the running project under the View Instructions tab.

Features:
- Displays registers being retrieved from on the selected line in red 
- Displays registers being set on the current line in green
- Autofills some code structures using Annotations
  - annotations are activated by typing @[annotation name] [arguments] and then enter
- Has two extra commands; DUMP and PRINT {reg} that can be used to view memory easily at points in the program
  - do note that these are NOT actual assembly instructions, and are just there to make debugging logic easier. 
  - further, these instructions are not in the greensheet for the language. They are however listed in the implemented commands list with the above warning
