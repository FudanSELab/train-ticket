/**
 * Created by dingding on 2017/10/13.
 */
var HOST=process.env.TICKET_OFFICE_MYSQL_HOST
var PORT=process.env.TICKET_OFFICE_MYSQL_PORT
var USER=process.env.TICKET_OFFICE_MYSQL_USER
var PASSWORD=process.env.TICKET_OFFICE_MYSQL_PASSWORD
var DATABASE=process.env.TICKET_OFFICE_MYSQL_DATABASE
var DB_CONN_STR = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
var MysqlClient = require('mysql').createConnection({
    host: HOST,
    user: USER,
    password: PASSWORD,
    database: DATABASE
});
var fs = require('fs');
var path = require('path');



var initData = function(callback){
    var sql = "CREATE TABLE IF NOT EXISTS office (name VARCHAR(255), city VARCHAR(255), province VARCHAR(255),region VARCHAR(255), address VARCHAR(255), workTime VARCHAR(32), windowNum INT(10))";
    MysqlClient.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Table created");
        callback(result);
    });

    // init data
    insertEntry('Jinqiao Road ticket sales outlets', 'Shanghai', 'Shanghai', 'Pudong New Area', 'Jinqiao Road 1320, Shanghai, Pudong New Area', '08:00-18:00', 1);

};

var getAllOffices = function(db, callback){
    MysqlClient.query("SELECT * FROM office", function (err, result, fields) {
        if (err) throw err;
        console.log(result);
        callback(result);
    });
};

/*根据省市区信息获取该地区的代售点列表*/
var getSpecificOffices = function(province, city, region, db, callback){
    var where_sql= "WHERE province = '" + province + "' AND city = '" + city + "' AND region = '" + region + "'";
    var sql = "SELECT * FROM office " + where_sql;
    console.log("getSpecificOffices sql:", sql);
    MysqlClient.query(sql, function (err, result, fields) {
        if (err) throw err;
        console.log(result);
        callback(result);
    });
};

/*根据省市区信息添加代售点*/
var addOffice = function(province, city, region, office, db, callback){
    insertEntry(office.name, city, province, region, office.address, office.workTime, office.windowNum);
    callback("insert succeed.")
};

/*根据省市区和代售点名称删除代售点*/
var deleteOffice = function(province, city, region, officeName, db, callback){
    var where_sql= "WHERE name = '" + officeName + "' AND province = '" + province + "' AND city = '" + city + "' AND region = '" + region + "'";
    var sql = "DELETE FROM office " + where_sql;
    MysqlClient.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Number of records deleted: " + result.affectedRows);
        callback(result);
    });
};


/*根据省市区代售点信息更新代售点*/
var updateOffice = function(province, city, region, oldOfficeName, newOffice, db, callback){
    var where_sql= "WHERE name = '" + oldOfficeName + "' AND province = '" + province + "' AND city = '" + city + "' AND region = '" + region + "'";
    var set_sql = "SET name = '" + newOffice.name + "', address = '" + newOffice.address + "', workTime = '" + newOffice.workTime + "', windowNum = " + newOffice.windowNum;
    var sql = "UPDATE office " + set_sql + " " + where_sql;
    console.log("update sql:", sql);
    MysqlClient.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Number of records updated: " + result.affectedRows);
        callback(result);
    });

};

var insertEntry = function(name, city, province, region, address, workTime, windowNum){
    values = "('" + name + "','" + city +"','" + province + "','" + region +"','"+address +"','"+workTime +"',"+windowNum+")";
    var sql = "INSERT INTO office (name, city, province, region, address, workTime, windowNum)" +
        " VALUES " + values;
    console.log("insert sql", sql);
    MysqlClient.query(sql, function (err, result) {
        if (err) throw err;
        console.log("1 record inserted, ", result);
    });
}

exports.initMysql = function(callback){
    MysqlClient.connect(function(err){
        console.log("initMysql连接上数据库啦！");
        initData(function(result){
            callback(result);
        });
    })
};

exports.getAll = function(callback){
    MysqlClient.connect(DB_CONN_STR, function(err, db){
        console.log("getAll连接上数据库啦！");
        getAllOffices(db, function(result){
            callback(result);
        });
    })
};

exports.getSpecificOffices = function(province, city, region, callback){
    MysqlClient.connect(DB_CONN_STR, function(err, db){
        console.log("getSpecificOffices连接上数据库啦！");
        getSpecificOffices(province, city, region, db, function(result){
            callback(result);
        });
    })
};

exports.addOffice = function(province, city, region, office, callback){
    MysqlClient.connect(DB_CONN_STR, function(err, db){
        console.log("addOffice连接上数据库啦！");
        addOffice(province, city, region, office, db, function(result){
            callback(result);
        });
    })
};

exports.deleteOffice = function(province, city, region, officeName, callback){
    MysqlClient.connect(DB_CONN_STR, function(err, db){
        console.log("deleteOffice连接上数据库啦！");
        deleteOffice(province, city, region, officeName, db, function(result){
            callback(result);
        });
    })
};

exports.updateOffice = function(province, city, region, oldOfficeName, newOffice, callback){
    MysqlClient.connect(DB_CONN_STR, function(err, db){
        console.log("updateOffice连接上数据库啦！");
        updateOffice(province, city, region, oldOfficeName, newOffice, db, function(result){
            callback(result);
        });
    })
};



