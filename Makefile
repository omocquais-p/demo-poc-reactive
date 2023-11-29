start-app:
	{ \
	set -e ;\
	docker compose --profile observability up ;\
	}

stop-app:
	{ \
	set -e ;\
	docker compose --profile observability down ;\
	}

build-image:
	{ \
	set -e ;\
	source /Users/omocquais/.sdkman/bin/sdkman-init.sh ;\
	sdk install java 17.0.8-tem  ;\
	sdk use java 17.0.8-tem  ;\
	./mvnw clean spring-boot:3.1.6-SNAPSHOT:build-image ;\
	}

call-api:
	./helpers/create-customers.sh

# Local - call the actuator endpoint
actuator-local:
	{ \
	set -e ;\
	./scripts/check-actuator-endpoint.sh http://localhost:8080 ;\
	}

# Local - call the API to create customers
customers-local:
	{ \
	set -e ;\
	./scripts/populate-customers.sh  http://localhost:8080 ;\
	}