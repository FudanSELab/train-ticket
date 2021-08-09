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

# license-check: Check source codes for Apache License
.PHONY: license-check clean-license-checker
license-check:
ifeq ("$(wildcard .actions/openwhisk-utilities/scancode/scanCode.py)", "")
	git clone https://github.com/apache/openwhisk-utilities.git .actions/openwhisk-utilities
	cp .actions/ASF* .actions/openwhisk-utilities/scancode/
endif
	.actions/openwhisk-utilities/scancode/scanCode.py ./

clean-license-checker:
	@rm -rf .actions/openwhisk-utilities
