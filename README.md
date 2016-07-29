# patients-be

Install:

	git clone https://github.com/kronstadt-imobiliare/patients-be.git
	
In mysql-workbench:

- open createDB.sql, execute all the commands
- import the csv files
	
Build:

	cd patients-be
	
	mvn clean package
	
The .war file will be generated in the 'target' folder.
