# Simian checker

It is a Java web application built upon spring framework to be used to identify if some DNA sequence represents a Simian or a human.

## Requirements
- Java 11
- Docker and Docker compose
- Apache maven

## Code coverage
After executing `mvn clean install`, you can see the code coverage report at: `target/site/jacoco/index.html`
## Installation

Using Docker

```bash
mvn clean install
docker build -t simian .
docker-compose up mongodb simian
```
## Usage
The URL of the service described bellow is for development local environment, 
for test environment consider these : 
```
http://ec2-15-228-36-16.sa-east-1.compute.amazonaws.com:8080/simian
http://ec2-15-228-36-16.sa-east-1.compute.amazonaws.com:8080/simian/stats
```
Note: The DNS cited above can stop responding if the ec2 instance was restarted,
if you have problems to reach out it please contact me by email: egamorim@gmail.com
 
##### Verify if some DNA sequence represents a Simian or a human
- A valid sequence to be analyzed must be an array of String where each 
element is a line of a square matrix.
- This matrix must be a square matrix (the same amount of columns and rows)
- The only characters allowed are: A, T, C, G
- To be considered a Simian the provided DNA sequence must have 
at least 1 sequence of 4 or more equal characters in any directions 
(vertical, horizontal or both diagonals) 

See an example of request bellow:
```
curl --request POST \
  --url http://localhost:8080/simian \
  --header 'Content-Type: application/json' \
  --data '{
"dna": ["CTGAGA", "CTGAGC", "TATTGT", "AGACTG", "CCGCTA", "TCACTG"]
}'
```
- In case of the provided DNA sequence was a Simian the response for that will 
be a Http status 200 (ok)
- In the case of the provided DNA sequence represents a human 
the response for that will be Http status 403 (forbidden)

##### Get the statistics about the percentage of Simian DNA

The result of this endpoint is the percentage of Simian considering the 
total amount of humans.
- example: If there are at the database 100 DNA sequences of humans and 40 sequences of Simians the result will be 40.00

```
curl --request GET \
  --url http://localhost:8080/simian/stats
```

Response:
```
{
  "countSimianDNA": 4,
  "countHumanDNA": 1,
  "ratio": 400.00
}
```