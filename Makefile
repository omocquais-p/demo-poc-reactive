run-app:
	./mvnw spring-boot:run -Pdocker-compose

call-api:
	./helpers/create-customers.sh