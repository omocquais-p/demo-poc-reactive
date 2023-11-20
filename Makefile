start-app:
	{ \
	set -e ;\
	docker compose --profile observability up ;\
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