<b><h2>Inroduction:</h2></b>
Here is log processing application. User can run it as jar or as docker image.
<p>By POST  http://localhost:8080/logs url this application recieve .log file with next format of data inside it:
<p>LogLevel{INFO|WARN|ERROR} DateTime{"yyyy-MM-dd HH:mm:ss.SSS»} Message{line|multiple lines}

After this user will be able to search log in inmemory H2 database with help of API endpoint GET http://localhost:8080/logs and query params:

<b><p> ?logLevel -  filters search result by log level</p></b>
<b><p> ?dateFrom - shows result that are elder or equal to dateTo</p></b>
<b><p> ?dateFrom - shows result that are younger or equal to dateTo</p></b>
<b><p> ?text - shows result that contains specified text</p></b>

User can combine queryparams. In this case user would receive respons that corresponds to all criteria of search


<b><h2>Optimization ideas:</h2></b>

<p> 1. Update API for processing file in stream while it is uploading and provide batch updates. For example save every 10000 records from List to DB
<p> 2. Provide Spring Security for authorization
<p> 3. Create Aspect that will convert and parse dates of any kind of view and pass it to API method
<p> 4. For using in browser provide base html page for response in separate endpoint


<b><h2>Run as docker image:</h2></b>
Just clone application to local env and run command:
<p>docker build -t velotix .
<p>docker run -d -p 8080:8080 velotix

Docker would automatically install all dependencies, run mvn clean install test and run application