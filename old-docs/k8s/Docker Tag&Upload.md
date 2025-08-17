# Tag and Uplaod your docker image.
The address of my docker registry is 10.141.211.160:5000.    
You could replace all 10.141.211.160:5000 with you own docker registry address.    

## Part 1:
docker tag ts/ts-admin-basic-info-service 10.141.211.160:5000/master/cluster-ts-admin-basic-info-service    
docker tag ts/ts-admin-order-service 10.141.211.160:5000/master/cluster-ts-admin-order-service    
docker tag ts/ts-admin-route-service 10.141.211.160:5000/master/cluster-ts-admin-route-service    
docker tag ts/ts-admin-travel-service 10.141.211.160:5000/master/cluster-ts-admin-travel-service    
docker tag ts/ts-admin-user-service 10.141.211.160:5000/master/cluster-ts-admin-user-service    
docker tag ts/ts-assurance-service 10.141.211.160:5000/master/cluster-ts-assurance-service    
docker tag ts/ts-basic-service 10.141.211.160:5000/master/cluster-ts-basic-service    
docker tag ts/ts-cancel-service 10.141.211.160:5000/master/cluster-ts-cancel-service    
docker tag ts/ts-config-service 10.141.211.160:5000/master/cluster-ts-config-service    
docker tag ts/ts-consign-service 10.141.211.160:5000/master/cluster-ts-consign-service    
docker tag ts/ts-contacts-service 10.141.211.160:5000/master/cluster-ts-contacts-service    
docker tag ts/ts-execute-service 10.141.211.160:5000/master/cluster-ts-execute-service    
docker tag ts/ts-food-map-service 10.141.211.160:5000/master/cluster-ts-food-map-service    
docker tag ts/ts-food-booking-service 10.141.211.160:5000/master/cluster-ts-food-booking-service    
docker tag ts/ts-inside-payment-service 10.141.211.160:5000/master/cluster-ts-inside-payment-service    
docker tag ts/ts-login-service 10.141.211.160:5000/master/cluster-ts-login-service    
docker tag ts/ts-user-notification-service 10.141.211.160:5000/master/cluster-ts-user-notification-service    
docker tag ts/ts-order-service 10.141.211.160:5000/master/cluster-ts-order-service    
docker tag ts/ts-payment-service 10.141.211.160:5000/master/cluster-ts-payment-service    
docker tag ts/ts-preserve-other-service 10.141.211.160:5000/master/cluster-ts-preserve-other-service    
docker tag ts/ts-preserve-service 10.141.211.160:5000/master/cluster-ts-preserve-service    
docker tag ts/ts-price-service 10.141.211.160:5000/master/cluster-ts-price-service    
docker tag ts/ts-rebook-service 10.141.211.160:5000/master/cluster-ts-rebook-service    
docker tag ts/ts-register-service 10.141.211.160:5000/master/cluster-ts-register-service    
docker tag ts/ts-route-service 10.141.211.160:5000/master/cluster-ts-route-service    
docker tag ts/ts-seat-service 10.141.211.160:5000/master/cluster-ts-seat-service    
docker tag ts/ts-security-service 10.141.211.160:5000/master/cluster-ts-security-service    
docker tag ts/ts-sso-service 10.141.211.160:5000/master/cluster-ts-sso-service    
docker tag ts/ts-station-service 10.141.211.160:5000/master/cluster-ts-station-service    
docker tag ts/ts-ticketinfo-service 10.141.211.160:5000/master/cluster-ts-ticketinfo-service    
docker tag ts/ts-train-service 10.141.211.160:5000/master/cluster-ts-train-service    
docker tag ts/ts-travel2-service 10.141.211.160:5000/master/cluster-ts-travel2-service    
docker tag ts/ts-travel-service 10.141.211.160:5000/master/cluster-ts-travel-service    
docker tag ts/ts-ticket-plan-service 10.141.211.160:5000/master/cluster-ts-ticket-plan-service    
docker tag ts/ts-ui-dashboard 10.141.211.160:5000/master/cluster-ts-ui-dashboard    
docker tag ts/ts-verification-code-service 10.141.211.160:5000/master/cluster-ts-verification-code-service    
docker tag ts/ts-voucher-service 10.141.211.160:5000/master/cluster-ts-voucher-service    
docker tag mongo 10.141.211.160:5000/master/cluster-ts-mongo    
docker tag mysql 10.141.211.160:5000/master/cluster-ts-mysql    
docker tag rabbitmq:management 10.141.211.160:5000/master/cluster-ts-rabbitmq-management    
docker tag redis 10.141.211.160:5000/master/cluster-ts-redis    
docker tag openzipkin/zipkin 10.141.211.160:5000/master/cluster-ts-openzipkin-zipkin    

## Part2
docker push 10.141.211.160:5000/master/cluster-ts-admin-basic-info-service    
docker push 10.141.211.160:5000/master/cluster-ts-admin-order-service    
docker push 10.141.211.160:5000/master/cluster-ts-admin-route-service    
docker push 10.141.211.160:5000/master/cluster-ts-admin-travel-service    
docker push 10.141.211.160:5000/master/cluster-ts-admin-user-service    
docker push 10.141.211.160:5000/master/cluster-ts-assurance-service    
docker push 10.141.211.160:5000/master/cluster-ts-basic-service    
docker push 10.141.211.160:5000/master/cluster-ts-cancel-service    
docker push 10.141.211.160:5000/master/cluster-ts-config-service    
docker push 10.141.211.160:5000/master/cluster-ts-consign-service    
docker push 10.141.211.160:5000/master/cluster-ts-contacts-service    
docker push 10.141.211.160:5000/master/cluster-ts-execute-service    
docker push 10.141.211.160:5000/master/cluster-ts-food-map-service    
docker push 10.141.211.160:5000/master/cluster-ts-food-booking-service    
docker push 10.141.211.160:5000/master/cluster-ts-inside-payment-service    
docker push 10.141.211.160:5000/master/cluster-ts-login-service    
docker push 10.141.211.160:5000/master/cluster-ts-user-notification-service    
docker push 10.141.211.160:5000/master/cluster-ts-order-service    
docker push 10.141.211.160:5000/master/cluster-ts-payment-service    
docker push 10.141.211.160:5000/master/cluster-ts-preserve-other-service    
docker push 10.141.211.160:5000/master/cluster-ts-preserve-service    
docker push 10.141.211.160:5000/master/cluster-ts-price-service    
docker push 10.141.211.160:5000/master/cluster-ts-rebook-service    
docker push 10.141.211.160:5000/master/cluster-ts-register-service    
docker push 10.141.211.160:5000/master/cluster-ts-route-service    
docker push 10.141.211.160:5000/master/cluster-ts-seat-service    
docker push 10.141.211.160:5000/master/cluster-ts-security-service    
docker push 10.141.211.160:5000/master/cluster-ts-sso-service    
docker push 10.141.211.160:5000/master/cluster-ts-station-service    
docker push 10.141.211.160:5000/master/cluster-ts-ticketinfo-service    
docker push 10.141.211.160:5000/master/cluster-ts-train-service    
docker push 10.141.211.160:5000/master/cluster-ts-travel2-service    
docker push 10.141.211.160:5000/master/cluster-ts-travel-service     
docker push 10.141.211.160:5000/master/cluster-ts-ticket-plan-service    
docker push 10.141.211.160:5000/master/cluster-ts-ui-dashboard    
docker push 10.141.211.160:5000/master/cluster-ts-verification-code-service    
docker push 10.141.211.160:5000/master/cluster-ts-voucher-service    
docker push 10.141.211.160:5000/master/cluster-ts-mongo    
docker push 10.141.211.160:5000/master/cluster-ts-mysql    
docker push 10.141.211.160:5000/master/cluster-ts-rabbitmq-management    
docker push 10.141.211.160:5000/master/cluster-ts-redis    
docker push 10.141.211.160:5000/master/cluster-ts-openzipkin-zipkin    