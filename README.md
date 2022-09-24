# Assembly-Info
an IDE for LEGv8 Assembly

Leg assembly is a simplified subset of ARMv8 assembly, specifically designed for teaching purposes. 
A greensheet for the language can be found here: https://montcs.bloomu.edu/Information/ARMv8/legv8-green-card.compressed.pdf
Currently, as the project is a work in progress, not all instructions are implemented. Fully Implemented instructions and usage are can be found in Info/Commands.txt, or displayed in the running project under the View Instructions tab.

- Displays registers being retrieved from in red and filled in green when the line is selected
- Autofills some code structures using @Annotations
- Has two extra commands; DUMP and PRINT {reg} that can be used to view memory easily at points in the program
