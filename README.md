April test task for GameSys.
Based on Java Spring Boot 2.2.6, Hibernate, JDBC, no JPA usage.

I use BitPay service as data source: that brings currency rate json data.
https://bitpay.com/

BitPay API documentation
https://bitpay.com/api/#rest-api-resources-rates-fetch-the-rates-used-by-bitpay-for-a-specific-cryptocurrency


Scheduled service download every 1 minute new currency rates and fills the local database.


Queries:

1. to show list of items in json format:
http://localhost:8081/showLatestRates?limit=10

2. to get item by Id in json
http://localhost:8081/getById?id=1

3. UI web page just made for testing issues
http://localhost:8081/


Also made a lot of unit and mock tests.


(optional) Create a Docker image for the project, so we can run this as a Docker container

Also I have created the Docker file, so it could be open with the command:

docker load --input apriltask.tar

docker run -p 3333:8080 apriltask

Image Docker file available with this link:
https://cloud.mail.ru/public/53HD/3cn4cidGW
