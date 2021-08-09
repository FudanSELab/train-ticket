-- Licensed to the Apache Software Foundation (ASF) under one or more
-- contributor license agreements.  See the NOTICE file distributed with
-- this work for additional information regarding copyright ownership.
-- The ASF licenses this file to You under the Apache License, Version 2.0
-- (the "License"); you may not use this file except in compliance with
-- the License.  You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

CREATE TABLE `voucherservice`.`voucher` (
  `voucher_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` VARCHAR(1024) NOT NULL,
  `travelDate` DATE NOT NULL,
  `travelTime` VARCHAR(1024) NOT NULL,
  `contactName` VARCHAR(1024) NOT NULL,
  `trainNumber` VARCHAR(1024) NOT NULL,
  `seatClass` INT NOT NULL,
  `seatNumber` VARCHAR(1024) NOT NULL,
  `startStation` VARCHAR(1024) NOT NULL,
  `destStation` VARCHAR(1024) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`voucher_id`));