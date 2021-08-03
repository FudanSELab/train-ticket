# Codewisdom Train-Ticket system

Username=codewisdom
Tag=0.1.0

# build image
.PHONY: build
build: clean-image package build-image

.PHONY: package
package:
	mvn clean package

.PHONY: build-image
build-image:
	@hack/build-image.sh $(Username) $(Tag)

# push image
.PHONY: push-image
push-image:
	@hack/push-image.sh $(Username)

.PHONY: clean
clean:
	@mvn clean
	@hack/clean-image.sh $(Username)

# clean image
.PHONY: clean-image
clean-image:
	@hack/clean-image.sh $(Username)
