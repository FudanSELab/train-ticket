/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by dingding on 2017/10/13.
 */
var MongoClient = require('mongodb').MongoClient;
var fs = require('fs');
var path = require('path');
// var DB_CONN_STR = 'mongodb://localhost:27017/test';
var DB_CONN_STR = 'mongodb://ts-ticket-office-mongo/ticket-office';

var initData = function(db, callback){
    var collection =  db.collection('office');
    if(collection.find()){
        collection.remove({});
    }
    /*读取已存在的数据*/
    fs.readFile(path.join(__dirname, "./office.json"), 'utf8', function (err, data) {
        data = JSON.parse( data );
        collection.insertMany(data, function(err, result){
            if(err){
                console.log('Error: ' + err);
                return;
            }
            callback(result);
        });
    });
};

var getAllOffices = function(db, callback){
    var collection =  db.collection('office');
    collection.find().toArray(function(err, result){
        if(err){
            console.log("Error:" + err);
            return;
        }
        callback(result);
    });
};

/*根据省市区信息获取该地区的代售点列表*/
var getSpecificOffices = function(province, city, region, db, callback){
    var collection =  db.collection('office');
    var findString = {"province":province ,
                        "city": city ,
                        "region": region};
    collection.find(findString).toArray(function(err, result){
        if(err){
            console.log("Error:" + err);
            return;
        }
        callback(result);
    });
};

/*根据省市区信息添加代售点*/
var addOffice = function(province, city, region, office, db, callback){
    var collection =  db.collection('office');
    var findString = {"province":province ,
                        "city": city ,
                        "region": region};
    var updateString = {$push:{
                            "offices": {
                                'officeName':office.officeName,
                                'address': office.address,
                                'workTime': office.workTime,
                                'windowNum': office.windowNum
                            }
                        }};
    collection.update(findString, updateString, function(err, result){
        if(err){
            console.log("Update Error:" + err);
            return;
        }
        callback(result);
    });
};

/*根据省市区和代售点名称删除代售点*/
var deleteOffice = function(province, city, region, officeName, db, callback){
    var collection =  db.collection('office');
    var findString = {
        "province":province ,
        "city": city ,
        "region": region
    };
    var updateString = {
        $pull:{
            "offices": {
                "officeName": officeName
            }
        }
    };
    collection.update(findString, updateString, function(err, result){
        if(err){
            console.log("Error:" + err);
            return;
        }
        callback(result);
    });
};


/*根据省市区代售点信息更新代售点*/
var updateOffice = function(province, city, region, oldOfficeName, newOffice, db, callback){
    var collection =  db.collection('office');
    var findString = {
        "province":province ,
        "city": city ,
        "region": region,
        "offices.officeName": oldOfficeName
    };
    var updateString = {
        $set:{
        'offices.$.officeName':newOffice.officeName,
        'offices.$.address': newOffice.address,
        'offices.$.workTime': newOffice.workTime,
        'offices.$.windowNum': newOffice.windowNum
        }
    };
    collection.update(findString, updateString, function(err, result){
        if(err){
            console.log("Error:" + err);
            return;
        }
        callback(result);
    });
};



exports.initMongo = function(callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("initMongo连接上数据库啦！");
        initData(db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.getAll = function(callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("getAll连接上数据库啦！");
        getAllOffices(db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.getSpecificOffices = function(province, city, region, callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("getSpecificOffices连接上数据库啦！");
        getSpecificOffices(province, city, region, db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.addOffice = function(province, city, region, office, callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("addOffice连接上数据库啦！");
        addOffice(province, city, region, office, db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.deleteOffice = function(province, city, region, officeName, callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("deleteOffice连接上数据库啦！");
        deleteOffice(province, city, region, officeName, db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.updateOffice = function(province, city, region, oldOfficeName, newOffice, callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("updateOffice连接上数据库啦！");
        updateOffice(province, city, region, oldOfficeName, newOffice, db, function(result){
            db.close();
            callback(result);
        });
    })
};



