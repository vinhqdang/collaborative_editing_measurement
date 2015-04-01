Collaborative Editor Evaluation
===============================
# Introduction

This tool is used for evaluating the performance of online real - time collaborative editing tool. 
Currently, the following services are supported:
1. [Google Docs](https://docs.google.com)
2. [MUTE](https://github.com/MatthieuNICOLAS/mute-demo)
3. [Etherpad](http://etherpad.org/)
4. [Cryptpad](https://github.com/cjdelisle/cryptpad)

# How to use
The application is provided as Java source code. Follows these steps to run the experiment by yourself, with Eclipse as IDE (you can use whatever IDE you prefer):
1. Open Eclipse and import the project.
2. Inside Eclipse, under 'src' folder, you will see different packages:
  * general: contains abstract classes
  * cryptpad: for Cryptpad
  * googledocs: for Google Docs
  * mute2: for MUTE
  * zohodocs: incomplete
3. Setting up the environment:
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
4. The code need to be updated before running. Let's say if you want to run Google Docs experiment.
  * Modify the *selenium_config.txt* according to your computer settings
For instance, in our case:
```
1.1.1.2 20
1.1.1.3 30
1.1.1.4
```
The last address is the remote server which take care all the unexpected request (for instance, if in runtime you have more than 50 requests).
   * You also need to modify the variable THRESHOLD in the same file, to indicate how many users will be simulated on computer A (the computer which run code)
```java
//inside the constructor of GoogleDocsAutomator
THRESHOLD = 10;
```

5. Setting up the parameter for experiment:
Open Main.java file under the package fr.inria.coast.googledocs
The following variables may be modified:
  * n_users: define what number of user you want to test
  * last_user, last_type, last_exp: define the last experiment so you can continue from the last time. If you start from beginning, just set all of them to 0.
  * out_file: the name of output file, so the results will be store in this file.
If everything is done, you can start by clicking to Run button of Eclipse.

6. Processing the result:
Suppose you set the out_file variable to "raw.txt"
You need to process this result file by using the provided Python script:
```
python processResult.py raw.txt final_output.txt
```
The result will be processed and stored at "final_output.txt" file.

7. You can visualize the result with R:
  * Open R and move to the directory of the code
  * Run the following R code:
```R
script(R_script.R)
#if you want to visualize the result with number of user is 10 and typing speed is 6
displayDelay ("final_output.txt",user=10,speed=6)
```
