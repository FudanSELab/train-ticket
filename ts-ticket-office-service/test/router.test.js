var sinon = require('sinon')
var db = require('../bin/db')

let init, getAll, getSpec, add, del, update;
init = sinon.stub(db,'initMongo').callsFake(function(callback){
    callback({result:{ok:true}});
})

getAll = sinon.stub(db, 'getAll').callsFake(function(callback){
    callback('getAll');
})

getSpec = sinon.stub(db, 'getSpecificOffices').callsFake(function(p, c, r, callback){
    callback('{officename:\'123\'}');
})

add = sinon.stub(db, 'addOffice').callsFake(function(p, c, r, o, callback){
    callback('addOffice');
})

del = sinon.stub(db, 'deleteOffice').callsFake(function(p, c, r, o, callback){
    callback('deleteOffice');
})

update = sinon.stub(db, 'updateOffice').callsFake(function(p, c, r, o, n, callback){
    callback('updateOffice');
})

var app = require('../app')
var request = require('supertest')

describe('office',  ()  =>{
    afterAll(() => {
        init.restore();
        getAll.restore();
        getSpec.restore();
        add.restore();
        del.restore();
        update.restore();
    });

    it('404', function(done) {
        request(app)
        .get('/')
        .expect(404, function(err, res) {
            done();
        });
    });

    it('/', function(done) {
        request(app)
        .get('/office')
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/getRegionList', function(done) {
        request(app)
        .get('/office/getRegionList')
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/getAll', function(done) {
        request(app)
        .get('/office/getAll')
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/getSpecificOffices', function(done) {
        request(app)
        .post('/office/getSpecificOffices')
        .send({
            province:'Shanghai',
            city:'Shanghai',
            region:'Pudong New Area'
        })
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/addOffice', function(done) {
        request(app)
        .post('/office/addOffice')
        .send({
            province:'Shanghai',
            city:'Shanghai',
            region:'Pudong New Area',
            office:[{
                "officeName": "Jinqiao Road ticket sales outlets",
                "address": "Jinqiao Road 1320, Shanghai, Pudong New Area",
                "workTime": "08:00-18:00",
                "windowNum": 1
                }]
        })
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/deleteOffice', function(done) {
        request(app)
        .post('/office/deleteOffice')
        .send({
            province:'Shanghai',
            city:'Shanghai',
            region:'Pudong New Area',
            officeName:"Jinqiao Road ticket sales outlets"
        })
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });

    it('/updateOffice', function(done) {
        request(app)
        .post('/office/updateOffice')
        .send({
            province:'Shanghai',
            city:'Shanghai',
            region:'Pudong New Area',
            officeName:"Jinqiao Road ticket sales outlets",
            newOffice:{
                "officeName": "Jinqiao Road ticket sales outlets",
                "address": "Jinqiao Road 1320, Shanghai, Pudong New Area",
                "workTime": "08:00-18:00",
                "windowNum": 1
                }
        })
        .expect('Content-Type', /json/)
        .expect(200, function(err, res) {
            done();
        });
    });
})

init.restore();

init = sinon.stub(db,'initMongo').callsFake(function(callback){
    callback({result:{ok:false}});
})

appfalse = require('../app')

describe('ok->flase branch',  ()  =>{
    afterAll(() => {
        init.restore();
    });

    it('/', function(done) {
        request(appfalse)
        .get('/office')
        .expect(200, function(err, res) {
            done();
        });
    })
})