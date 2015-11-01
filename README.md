# Web Application Performance Testing Tool #

## Developer Setup ##
\begin{itemize}
\item - Import `pom.xml` into your IDE of choice. (For Ext JAR files: JSoup, commons-lang3, logback-classic [for future works])
\item - Run target web application in Dev mode - Production versions usually ban the tool
\item - Run the program from MainWindow.java
\end{itemize}

## User Setup ##
Run WAPT Tool jar file, and follow below workflows:

\subsubsection{Basic page retrieval}
 \begin{itemize}
    \item The user enters the base URI in the format: \\http[s]://[www.]host.com
    \item Next, the user enters wait time, number of tries and number of threads required in the performance test.
    \item Optionally, the user may wish to define where to output the result CSV file by clicking 'Set Output File'
    \item Afterwards, the user enters the name of the page to be tested, for example "/index.html" or "/", and clicks the Add button. This is repeated until all required pages are added to the table.
    \item Finally, the user clicks Run, and the tool begins testing.
  \end{itemize} 


\subsubsection{Testing a login form}

\begin{itemize}
  \item The user enters the base URI in the format: \\http[s]://[www.]host.com/loginform
\item Next, the user enters wait time, number of tries and number of threads required in the performance test.
  \item Then, the user clicks 'List Parameters', and all available form submission parameters on the webpage are displayed. Typically this will be "user" and "pass", however web developers are free to name their parameters as they wish.
  \item The user then picks the username parameter and clicks 'Set UserParameter', and does the same for the password parameter.
  \item At this point, the user sets the Base URI back to the format: \\http[s]://[www.]host.com
  \item The user then enters the tested username and password in the tool.
  \item Optionally, the user may wish to define where to output the result CSV file by clicking 'Set Output File'
  \item Afterwards, the user enters the name of the page with a login form to be tested, for example "/login" or "/", selects 'Login Form?', and clicks the Add button.
  \item The webpage immediately following the login form must be the URI address of the POST request submitted by the login form, and 'POST Login URI?' must be ticked. In the case of some PHP web applications, this happens to be the same URI as the login form.
  \item Once the Login Form and POST Login URI are declared, the user can repeat adding webpages until all required pages are added to the table.
  %  This is repeated until all required pages are added to the table.
  \item Finally, the user clicks Run, and the tool begins testing.
\end{itemize}




## User Interface ##

The user interface of the proposed web application performance testing tool contains the following elements:

\begin{itemize}
      \item Base URI - the base URI of the web application to be tested
      \item Wait time - pause time between each try
      \item Number of tries - the number of times to loop through the set of web pages
      \item Number of threads - the number of simultaneous users being simulated
      \item List Parameters - populates a list of available parameters to be used as username and password parameters for login form submission
      \item Set UserParameter - sets the selected parameter as the username parameter
      \item Set PassParameter - sets the selected parameter as the password parameter
      \item Username - the username to be submitted into the login form
      \item Password - the password to be submitted into the login form
      \item Set Output File - specifies the location to which the output CSV will be saved
      \item Page to test - a textbox for adding webpages to the test workload
      \item Login Form? - if the URI submitted into 'Page to test' contains a login form, this box must be ticked
      \item POST Login URI? - the URI to which the form submits a POST request to must then immediately follow. This box lets the tool know where to send the Username and Password parameters.
      \item Add - adds a webpage to the test workload
      \item Remove - removes a webpage from the test workload
      \item Run - runs the configured test
\end{itemize}