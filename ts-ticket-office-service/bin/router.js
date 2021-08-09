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

var express = require('express');
var router = express.Router();

var fs = require('fs');
var path = require('path');

var db = require('./db');

router.get('/', function(req, res, next) {
    res.send("welcome to ts-ticket-office-service");
});

// router.get('/init', function(req, res, next) {
//     db.initMongo(function(result){
//         res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//         result = JSON.stringify(result);
//         console.log("initResult=" + result);
//         res.end(result);
//     });
// });

router.get('/getRegionList', function(req, res, next) {
    /*读取region.json*/
    fs.readFile(path.join(__dirname, "./region.json"), 'utf8', function (err, data) {
        data = JSON.parse( data );
        console.log("getRegionList=" + JSON.stringify(data));
        res.json(data);
    });
});

router.get('/getAll', function(req, res, next) {
    db.getAll(function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        console.log("getAll=" + JSON.stringify(result));
        res.json(result);
    });
});

router.post('/getSpecificOffices', function(req, res, next) {
    db.getSpecificOffices(req.body.province, req.body.city, req.body.region, function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        console.log("getSpecificOffices=" + JSON.stringify(result));
        res.json(result);
    });
});

router.post('/addOffice', function(req, res, next) {
    db.addOffice(req.body.province, req.body.city, req.body.region, req.body.office, function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        console.log("addOffice=" + JSON.stringify(result));
        res.json(result);
    });
});

//此处注意，同一个区默认不能有两个名字相同的代售点
router.post('/deleteOffice', function(req, res, next) {
    db.deleteOffice(req.body.province, req.body.city, req.body.region, req.body.officeName, function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        console.log("deleteOffice=" + JSON.stringify(result));
        res.json(result);
    });
});

//此处注意，同一个区默认不能有两个名字相同的代售点
router.post('/updateOffice', function(req, res, next) {
    db.updateOffice(req.body.province, req.body.city, req.body.region, req.body.oldOfficeName, req.body.newOffice, function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        console.log("updateOffice=" + JSON.stringify(result));
        res.json(result);
    });
});

module.exports = router;
