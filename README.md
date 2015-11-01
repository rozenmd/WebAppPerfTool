# Web Application Performance Testing Tool #

## Developer Setup ##

-Import `pom.xml` into your IDE of choice. (For Ext JAR files: JSoup, commons-lang3, logback-classic [for future works])
-Run target web application in Dev mode - Production versions usually ban the tool
-Run the program from MainWindow.java


## User Setup ##
Run WAPT Tool jar file, and follow below workflows:

## Basic page retrieval ##

-The user enters the base URI in the format: \\http[s]://[www.]host.com
-Next, the user enters wait time, number of tries and number of threads required in the performance test.
-Optionally, the user may wish to define where to output the result CSV file by clicking 'Set Output File'
-Afterwards, the user enters the name of the page to be tested, for example "/index.html" or "/", and clicks the Add button. This is repeated until all required pages are added to the table.
-Finally, the user clicks Run, and the tool begins testing.
 


## Testing a login form ##
-The user enters the base URI in the format: \\http[s]://[www.]host.com/loginform
-Next, the user enters wait time, number of tries and number of threads required in the performance test.
-Then, the user clicks 'List Parameters', and all available form submission parameters on the webpage are displayed. Typically this will be "user" and "pass", however web developers are free to name their parameters as they wish.
-The user then picks the username parameter and clicks 'Set UserParameter', and does the same for the password parameter.
-At this point, the user sets the Base URI back to the format: \\http[s]://[www.]host.com
-The user then enters the tested username and password in the tool.
-Optionally, the user may wish to define where to output the result CSV file by clicking 'Set Output File'
-Afterwards, the user enters the name of the page with a login form to be tested, for example "/login" or "/", selects 'Login Form?', and clicks the Add button.
-The webpage immediately following the login form must be the URI address of the POST request submitted by the login form, and 'POST Login URI?' must be ticked. In the case of some PHP web applications, this happens to be the same URI as the login form.
-Once the Login Form and POST Login URI are declared, the user can repeat adding webpages until all required pages are added to the table.
  %  This is repeated until all required pages are added to the table.
-Finally, the user clicks Run, and the tool begins testing.





## User Interface ##

The user interface of the proposed web application performance testing tool contains the following elements:


-Base URI - the base URI of the web application to be tested
-Wait time - pause time between each try
-Number of tries - the number of times to loop through the set of web pages
-Number of threads - the number of simultaneous users being simulated
-List Parameters - populates a list of available parameters to be used as username and password parameters for login form submission
-Set UserParameter - sets the selected parameter as the username parameter
-Set PassParameter - sets the selected parameter as the password parameter
-Username - the username to be submitted into the login form
-Password - the password to be submitted into the login form
-Set Output File - specifies the location to which the output CSV will be saved
-Page to test - a textbox for adding webpages to the test workload
-Login Form? - if the URI submitted into 'Page to test' contains a login form, this box must be ticked
-POST Login URI? - the URI to which the form submits a POST request to must then immediately follow. This box lets the tool know where to send the Username and Password parameters.
-Add - adds a webpage to the test workload
-Remove - removes a webpage from the test workload
-Run - runs the configured test
