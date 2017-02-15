Collaborative Editor Evaluation
===============================
# Introduction

This tool is used for evaluating the performance of online real - time collaborative editing tool. 
Currently, the following services are supported:
* [Google Docs](https://docs.google.com)
* [MUTE](https://github.com/MatthieuNICOLAS/mute-demo)
* [Etherpad](http://etherpad.org/)
* [Cryptpad](https://github.com/cjdelisle/cryptpad)

# How to use
The application is provided as Java source code. Follows these steps to run the experiment by yourself (tested with Eclipse, however it is supposed to be runable without depending on IDE.)

* First of all you need to download a correct [chromedriver](http://chromedriver.storage.googleapis.com/index.html) version for your operating system and put the file at the root directory of the project. In \*nix system, you may need to grant the *execute* permission.

* Open Eclipse and import the project.
* Inside Eclipse, under 'src' folder, you will see different packages:
  * general: contains abstract classes
  * cryptpad: for Cryptpad
  * googledocs: for Google Docs
  * mute2: for MUTE
  * zohodocs: incomplete
* Setting up the environment:
  * It is recommended that you run the experiment with multiple computers than one.
Let's say, if your main computer where you stored the source code is computer 1 with IP address 1.1.1.1
You have other two computers which join the experiment, B and C, with corresponding IP address is 1.1.1.2 and 1.1.1.3
You plan to simulate 20 users with computer B and 30 users with user C.
You also plan to simulate 10 users with computer A.
  * In computer B and C, please downnload and run [Selenium Server](http://www.seleniumhq.org/download/) with corresponding [Google Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/).
Put selenium server and chromedriver on the same directory.
Remember to set the execution mod (chmod +x chromedriver) if needed.
Then, run the selenium server on both computer B and C by using the command
```
cd /path/to/selenium_server
java -jar /selenium_server/jar/file
```
* The code need to be updated before running. Let's say if you want to run Google Docs experiment.
  * Modify the *selenium_config.txt* according to your computer settings
For instance, in our case:
```
LOCAL 10
1.1.1.2 20
1.1.1.3 30
1.1.1.4
```

The first line tells us that there is maximum 10 threads will be executed in the local (or your machine).

There will be up to 20 threads will be run on the machine with IP address 1.1.1.2.

The last address is the remote server which take care all the unexpected request (for instance, if in runtime you have more than 50 requests).

* Setting up the parameter for experiment:

	* In *num_user_setting.txt* file, you can set up the number of users which will be simulated. The content of the file looks like:

	```
	1 5 10 20 50
	```
	
	It means, the program will simulate the system with 1 user, 5 users, etc.
	
	* The file *last_exp_info.txt* should be updated automatically during experiment, but in the beginning it should contain

	```
	NUM_USER 0 
	TYPE_SPD 0
	EXP_ID 0
	```
	
	The content of this file is the last experiment configuration, so you can stop and restart the experiment in next time.

* If everything is done, you can start by clicking to Run button of Eclipse.

* Processing the result:
Suppose you set the out_file variable to "raw.txt"
You need to process this result file by using the provided Python script:
```
python processResult.py raw.txt final_output.txt
```
The result will be processed and stored at "final_output.txt" file.

* You can visualize the result with R:
  * Open R and move to the directory of the code
  * Run the following R code:
```R
source("R_script.R")
#if you want to visualize the result with number of user is 10 and typing speed is 6
displayDelay ("final_output.txt",user=10,speed=6)
```
