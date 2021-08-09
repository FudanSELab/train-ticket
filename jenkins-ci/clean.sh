#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

sudo docker rmi codewisdom/ts-train-service:latest
sudo docker rmi codewisdom/ts-ui-dashboard:latest
sudo docker rmi codewisdom/ts-auth-service:latest
sudo docker rmi codewisdom/ts-user-service:latest
sudo docker rmi codewisdom/ts-verification-code-service:latest
sudo docker rmi codewisdom/ts-contacts-service:latest
sudo docker rmi codewisdom/ts-order-service:latest
sudo docker rmi codewisdom/ts-order-other-service:latest
sudo docker rmi codewisdom/ts-config-service:latest
sudo docker rmi codewisdom/ts-station-service:latest
sudo docker rmi codewisdom/ts-travel-service:latest
sudo docker rmi codewisdom/ts-travel2-service:latest
sudo docker rmi codewisdom/ts-preserve-service:latest
sudo docker rmi codewisdom/ts-preserve-other-service:latest
sudo docker rmi codewisdom/ts-basic-service:latest
sudo docker rmi codewisdom/ts-ticketinfo-service:latest
sudo docker rmi codewisdom/ts-price-service:latest
sudo docker rmi codewisdom/ts-notification-service:latest
sudo docker rmi codewisdom/ts-security-service:latest
sudo docker rmi codewisdom/ts-inside-payment-service:latest
sudo docker rmi codewisdom/ts-execute-service:latest
sudo docker rmi codewisdom/ts-payment-service:latest
sudo docker rmi codewisdom/ts-rebook-service:latest
sudo docker rmi codewisdom/ts-cancel-service:latest
sudo docker rmi codewisdom/ts-route-service:latest
sudo docker rmi codewisdom/ts-assurance-service:latest
sudo docker rmi codewisdom/ts-seat-service:latest
sudo docker rmi codewisdom/ts-travel-plan-service:latest
sudo docker rmi codewisdom/ts-route-plan-service:latest
sudo docker rmi codewisdom/ts-food-map-service:latest
sudo docker rmi codewisdom/ts-food-service:latest
sudo docker rmi codewisdom/ts-consign-price-service:latest
sudo docker rmi codewisdom/ts-consign-service:latest
sudo docker rmi codewisdom/ts-admin-order-service:latest
sudo docker rmi codewisdom/ts-admin-basic-info-service:latest
sudo docker rmi codewisdom/ts-admin-route-service:latest
sudo docker rmi codewisdom/ts-admin-travel-service:latest
sudo docker rmi codewisdom/ts-admin-user-service:latest
sudo docker rmi codewisdom/ts-news-service:latest
sudo docker rmi codewisdom/ts-ticket-office-service:latest
sudo docker rmi codewisdom/ts-voucher-service:latest

sudo docker rmi ts/ts-train-service:latest
sudo docker rmi ts/ts-ui-dashboard:latest
sudo docker rmi ts/ts-auth-service:latest
sudo docker rmi ts/ts-user-service:latest
sudo docker rmi ts/ts-verification-code-service:latest
sudo docker rmi ts/ts-contacts-service:latest
sudo docker rmi ts/ts-order-service:latest
sudo docker rmi ts/ts-order-other-service:latest
sudo docker rmi ts/ts-config-service:latest
sudo docker rmi ts/ts-station-service:latest
sudo docker rmi ts/ts-travel-service:latest
sudo docker rmi ts/ts-travel2-service:latest
sudo docker rmi ts/ts-preserve-service:latest
sudo docker rmi ts/ts-preserve-other-service:latest
sudo docker rmi ts/ts-basic-service:latest
sudo docker rmi ts/ts-ticketinfo-service:latest
sudo docker rmi ts/ts-price-service:latest
sudo docker rmi ts/ts-notification-service:latest
sudo docker rmi ts/ts-security-service:latest
sudo docker rmi ts/ts-inside-payment-service:latest
sudo docker rmi ts/ts-execute-service:latest
sudo docker rmi ts/ts-payment-service:latest
sudo docker rmi ts/ts-rebook-service:latest
sudo docker rmi ts/ts-cancel-service:latest
sudo docker rmi ts/ts-route-service:latest
sudo docker rmi ts/ts-assurance-service:latest
sudo docker rmi ts/ts-seat-service:latest
sudo docker rmi ts/ts-travel-plan-service:latest
sudo docker rmi ts/ts-route-plan-service:latest
sudo docker rmi ts/ts-food-map-service:latest
sudo docker rmi ts/ts-food-service:latest
sudo docker rmi ts/ts-consign-price-service:latest
sudo docker rmi ts/ts-consign-service:latest
sudo docker rmi ts/ts-admin-order-service:latest
sudo docker rmi ts/ts-admin-basic-info-service:latest
sudo docker rmi ts/ts-admin-route-service:latest
sudo docker rmi ts/ts-admin-travel-service:latest
sudo docker rmi ts/ts-admin-user-service:latest
sudo docker rmi ts/ts-news-service:latest
sudo docker rmi ts/ts-ticket-office-service:latest
sudo docker rmi ts/ts-voucher-service:latest

sudo docker rmi $(docker image ls -qf dangling=true)
sudo docker image prune -f