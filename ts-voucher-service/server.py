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

#coding:utf-8
import tornado.ioloop
import tornado.web
import json
import pymysql
import urllib
import urllib.request

class GetVoucherHandler(tornado.web.RequestHandler):

    def post(self, *args, **kwargs):
        #Analyze the data transferred: order id and model indicator (0 stands for ordinary, 1 stands for bullet trains and high-speed trains)
        data = json.loads(self.request.body)
        orderId = data["orderId"]
        type = data["type"]
        #Query for the existence of a corresponding credential based on the order id
        queryVoucher = self.fetchVoucherByOrderId(orderId)

        if(queryVoucher == None):
            #Request the order details based on the order id
            orderResult = self.queryOrderByIdAndType(orderId,type)
            order = orderResult['data']

            # jsonStr = json.dumps(orderResult)
            # self.write(jsonStr)

            #Insert vouchers table into a voucher
            config = {
                'host':'ts-voucher-mysql',
                'port':3306,
                'user':'root',
                'password':'root',
                'db':'voucherservice'
            }
            conn = pymysql.connect(**config)
            cur = conn.cursor()
            #Insert statement
            sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
            try:
                cur.execute(sql,(order['id'],order['travelDate'],order['travelTime'],order['contactsName'],order['trainNumber'],order['seatClass'],order['seatNumber'],order['from'],order['to'],order['price']))
                conn.commit()
            finally:
                conn.close()
            #Query again to get the credential information just inserted
            self.write(self.fetchVoucherByOrderId(orderId))
        else:
            self.write(queryVoucher)

    def queryOrderByIdAndType(self,orderId,type):
        type = int(type)
        #ordinary train
        if(type == 0):
            url='http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/' + orderId
        else:
            url='http://ts-order-service:12031/api/v1/orderservice/order/'+orderId
        header_dict = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko',"Content-Type": "application/json"}
        req = urllib.request.Request(url=url,headers=header_dict)# Generate the full data for the page request
        response = urllib.request.urlopen(req)# Send page request
        return json.loads(response.read())# Gets the page information returned by the server

    def fetchVoucherByOrderId(self,orderId):
        #Check the voucher for reimbursement for orderId from the voucher table
        config = {
            'host':'ts-voucher-mysql',
            'port':3306,
            'user':'root',
            'password':'root',
            'db':'voucherservice'
        }
        conn = pymysql.connect(**config)
        cur = conn.cursor()
        #query statement
        sql = 'SELECT * FROM voucher where order_id = %s'
        try:
            cur.execute(sql,(orderId))
            voucher = cur.fetchone()
            conn.commit()
            #Build return data
            if(cur.rowcount < 1):
                return None
            else:
                voucherData = {}
                voucherData['voucher_id'] = voucher[0]
                voucherData['order_id'] = voucher[1]
                voucherData['travelDate'] = voucher[2]
                voucherData['contactName'] = voucher[4]
                voucherData['train_number'] = voucher[5]
                voucherData['seat_number'] = voucher[7]
                voucherData['start_station'] = voucher[8]
                voucherData['dest_station'] = voucher[9]
                voucherData['price'] = voucher[10]
                jsonStr = json.dumps(voucherData)
                print(jsonStr)
                return jsonStr
        finally:
            conn.close()

def make_app():
    return tornado.web.Application([
        (r"/getVoucher", GetVoucherHandler)
    ])

def initDatabase():
    config = {
        'host':'ts-voucher-mysql',
        'port':3306,
        'user':'root',
        'password':'root'
    }
    # Create a connection
    connect = pymysql.connect(**config)
    cur = connect.cursor()
    #create db
    sql = "CREATE SCHEMA IF NOT EXISTS voucherservice;"
    try:
        cur.execute(sql)
        connect.commit()
    finally:
        pass

    #Use the database
    sql = "use voucherservice;"
    try:
        cur.execute(sql)
        connect.commit()
    finally:
        pass

    #Create the table
    sql = """
    CREATE TABLE if not exists voucherservice.voucher (
    voucher_id INT NOT NULL AUTO_INCREMENT,
    order_id VARCHAR(1024) NOT NULL,
    travelDate VARCHAR(1024) NOT NULL,
    travelTime VARCHAR(1024) NOT NULL,
    contactName VARCHAR(1024) NOT NULL,
    trainNumber VARCHAR(1024) NOT NULL,
    seatClass INT NOT NULL,
    seatNumber VARCHAR(1024) NOT NULL,
    startStation VARCHAR(1024) NOT NULL,
    destStation VARCHAR(1024) NOT NULL,
    price FLOAT NOT NULL,
    PRIMARY KEY (voucher_id));"""
    try:
        cur.execute(sql)
        connect.commit()
    finally:
        connect.close()

if __name__ == "__main__":
    #Create database and tables
    initDatabase()
    app = make_app()
    app.listen(16101)
    tornado.ioloop.IOLoop.current().start()


    