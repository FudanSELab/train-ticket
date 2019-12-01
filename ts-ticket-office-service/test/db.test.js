var db = require('../bin/db');

const collection = require('mongodb').Collection;
const mongo = require('mongodb').MongoClient;
const sinon=require('sinon');


let cnct, mongoCollection, mongodb;

let province = 'Shanghai';
    let city = 'Shanghai';
    let region = 'Pudong New Area';
    var findString = {"province":province ,
                        "city": city ,
                        "region": region};
    var ret = [ { "officeName" : "Jinqiao Road ticket sales outlets", "address" : "Jinqiao Road 1320, Shanghai, Pudong New Area", "workTime" : "08:00-18:00", "windowNum" : 1 }, { "officeName" : "Chuansha road first ticket agency", "address" : "No.5273, Chuansha Road, Shanghai, Pudong New Area", "workTime" : "08:00-17:30", "windowNum" : 1 } ]
    var office = { "officeName" : "Jinqiao Road ticket sales outlets", "address" : "Jinqiao Road 1320, Shanghai, Pudong New Area", "workTime" : "08:00-18:00", "windowNum" : 1 }
    var name = "Jinqiao Road ticket sales outlets"

beforeEach(() => {
    //自定义db和collection
    mongoCollection={
    }
    mongodb ={
        mongoCollection: mongoCollection,
        collection:function(name){return this.mongoCollection},
        close: sinon.stub()
    };
    cnct = sinon.stub(mongo, 'connect');
    cnct.callsFake(function(dstr, callback){//将connect函数换为自定义的这个函数
        callback(null ,mongodb);//返回自定义的mongodb对象
    })
  });
  

afterEach(() => {
    cnct.restore();
});

describe('init',  ()  =>{
    it('initMongo', done => {
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        mongoCollection.find = sinon.stub().returns(false);
        mongoCollection.insertMany = sinon.stub().callsFake(function(data, callback){
            callback(null, null)
        });
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.initMongo(fn);
    
    
    })

    it('initMongo-find&err', done => {
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        mongoCollection.find = sinon.stub().returns(true);
        mongoCollection.insertMany = sinon.stub().callsFake(function(data, callback){
            callback('init error', null);
            return;
        });
        mongoCollection.remove = sinon.stub();
        const fn = jest.fn(function (result){
            done();
        });
        // 传入测试函数
        db.initMongo(fn);

    })
})

describe('getAll',  ()  =>{
    it('getAll', done => {
        var fake = {
            toArray: sinon.stub().callsFake(function(callback){
                callback(null, null);
                return;
            })
        };
        mongoCollection.find = sinon.stub().returns(fake)
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.getAll(fn);
    })

    it('getAll-err', done => {
        var fake = {
            toArray: sinon.stub().callsFake(function(callback){
                callback('getAll error', null);
                return;
            })
        };
        mongoCollection.find = sinon.stub().returns(fake)
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.getAll(fn);
    })

})

describe('getSpecific',  ()  =>{
    
    it('getSpecific', done => {
        var fake = {
            toArray: sinon.stub().callsFake(function(callback){
                callback(null, ret);
                return;
            })
        };
        mongoCollection.find = sinon.stub().withArgs(findString).returns(fake)
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            expect(fn).toHaveBeenCalledWith(ret);
            done();
        });
        // 传入测试函数
        db.getSpecificOffices(province, city, region, fn);
    })

    it('getSpecific-err', done => {
        var fake = {
            toArray: sinon.stub().callsFake(function(callback){
                callback('getSpecific error', null);
                return;
            })
        };
        mongoCollection.find = sinon.stub().returns(fake)
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.getSpecificOffices(province, city, region, fn);
    })

})

describe('addOffice',  ()  =>{
    
    it('addOffice', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback(null, 'ok');
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            expect(fn).toHaveBeenCalledWith('ok');
            done();
        });
        // 传入测试函数
        db.addOffice(province, city, region, ret, fn);
    })

    it('addOffice-err', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback('addOffice error', null);
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.addOffice(province, city, region, office, fn);
    })
})

describe('deleteOffice',  ()  =>{
    
    it('deleteOffice', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback(null, 'ok');
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            expect(fn).toHaveBeenCalledWith('ok');
            done();
        });
        // 传入测试函数
        db.deleteOffice(province, city, region, name, fn);
    })

    it('deleteOffice-err', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback('deleteOffice error', null);
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.deleteOffice(province, city, region, name, fn);
    })
})

describe('updateOffice',  ()  =>{
    
    it('updateOffice', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback(null, 'ok');
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            expect(fn).toHaveBeenCalledWith('ok');
            done();
        });
        // 传入测试函数
        db.updateOffice(province, city, region, name, office, fn);
    })

    it('updateOffice-err', done => {
        mongoCollection.update = sinon.stub().callsFake(function(strA, strB, callback){
            callback('updateOffice error', null);
            return;
        })
        //将函数中使用到的方法全都mock掉，根据不同分支更改返回值
        const fn = jest.fn(function (result){
            expect(fn).toHaveBeenCalled();
            expect(fn).toHaveBeenCalledTimes(1);
            done();
        });
        // 传入测试函数
        db.updateOffice(province, city, region, name, office, fn);
    })
})