Here are few assumptions considered.

1. Employee and Address can have many to many relationships. Employees (Brother and Sister) can share address. One employee can have multiple address such as office address, home address etc.
   
2. Since single employee can have more than one address, Employee will have list of addresses.

For Idempotent behavior, key named 'idempotency_key' is expected to come in Create employee POST API call.
For now, we are storing these keys in Database. We can have TTL and remove them instead of keeping them for life long.


CURL for Create Employee POST API:

curl --location --request POST 'http://localhost:8080/v1/bfs/employees' \
--header 'idempotency_key: fdfd767dfdfd' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "Sunny 1",
"lastName": "Garg 1",
"dateOfBirth": "28 October, 1987",
"addresses": [{
"line1": "test line 1",
"line2": "test line 2",
"city": "test city",
"state": "test state",
"country": "test country",
"zipCode": "test zip code"
}]
}'


CURL for Get Employee API:

curl --location --request GET 'http://localhost:8080/v1/bfs/employees/dab93a5d-980a-4d13-9c6c-5bd82591634f1614441002912'