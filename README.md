April test task for GameSys.
Based on Java Spring Boot 2.2.6, Hibernate, JDBC, no JPA usage.


I use BitPay service as data source: that brings currency rate json data, because rate changes very often.
https://bitpay.com/

BitPay API documentation
https://bitpay.com/api/#rest-api-resources-rates-fetch-the-rates-used-by-bitpay-for-a-specific-cryptocurrency


Scheduled service download fresh currency rate every minute and fills up the local database.


Queries:

1. to show list of items in json format:
http://localhost:8080/showLatestRates?limit=10

2. to get item by Id in json
http://localhost:8080/getById?id=1

3. UI web page have been made just for testing issues
http://localhost:8080/


to start application with console:
java -jar -Dserver.port=8080 target/task-0.0.1-SNAPSHOT.jar


Also made a lot of unit and mock tests.


(optional)
Also I have created the Docker file, so it could be opened with the command:

docker load --input apriltask.tar

docker run -p 3333:8080 apriltask

Image Docker file available with this URL:
https://cloud.mail.ru/public/53HD/3cn4cidGW

