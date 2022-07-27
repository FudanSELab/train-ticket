
curl -X POST -H "content-type: application/json" \
 http://127.0.0.1:16108/office/updateOffice \
 -d '{"province": "shanghai", "city":"shanghai", "region": "Pudong New Area",
     "oldOfficeName":  "test1",
     "newOffice": {
           "name": "test2",
            "address": "address1",
           "workTime": "0800-18:00",
           "windowNum": 3}}'

# curl -X POST -H "content-type: application/json" \
# http://127.0.0.1:16108/office/addOffice \
# -d '{"province": "shanghai", "city":"shanghai", "region": "Pudong New Area",
#    "office": {
#       "name": "test2",
#        "address": "address1",
#        "workTime": "0800-18:00",
#        "windowNum": 3}}'

#curl -X POST -H "content-type: application/json" -H "'Encodeing':'utf8'" http://127.0.0.1:16108/office/getSpecificOffices -d '{"province": "shanghai", "city":"shanghai", "region": "Pudong New Area"}'