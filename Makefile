# Codewisdom Train-Ticket system

Repo=hdali00/train-ticket
Tag=train-ticket
Namespace="default"
DeployArgs=""


# build image
.PHONY: build
build: clean-image package build-image

.PHONY: package
package:
	@mvn clean package -Dmaven.test.skip=true

.PHONY: build-image
build-image:
	@hack/build-image.sh $(Repo) $(Tag)

# push image
.PHONY: push-image
push-image:
	@hack/push-image.sh $(Repo)

.PHONY: publish-image
publish-image:
	@script/publish-docker-images.sh $(Repo) $(Tag)

# deploy
# DeployArgs ""                    : deploy train-ticket with all-in-one mysql cluster
# DeployArgs "--independent-db"    : deploy train-ticket with mysql cluster each service
# DeployArgs "--with-monitoring"   : deploy train-ticket with prometheus
# DeployArgs "--with-tracing"      : deploy train-ticket with skywalking
# DeployArgs "--all"               : deploy train-ticket with mysql cluster each service
.PHONY: deploy
deploy:
	@hack/deploy/deploy.sh $(Namespace) "$(DeployArgs)"

# deploy
.PHONY: reset-deploy
reset-deploy:
	@hack/deploy/reset.sh $(Namespace)

.PHONY: clean
clean:
	@mvn clean
	@hack/clean-image.sh $(Repo)

# clean image
.PHONY: clean-image
clean-image:
	@hack/clean-image.sh $(Repo)
