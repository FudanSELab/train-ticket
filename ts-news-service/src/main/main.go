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

package main

import (
	"github.com/hoisie/web"
	//"labix.org/v2/mgo"
	//"labix.org/v2/mgo/bson"
	//"fmt"
)

//const (
//	MONGODB_URL = "ts-news-mongo:27017"
//)

//func getNews(val string) string {
//

//	session, err := mgo.Dial(MONGODB_URL)
//	if err != nil {
//		panic(err)
//	}
//	defer session.Close()
//
//	session.SetMode(mgo.Monotonic, true)
//	// db := session.DB("xtest")
//	// collection := db.C("xtest")
//	c := session.DB("xtest").C("xtest")
//

//	 var personAll []News
//	 err = c.Find(nil).All(&personAll)
//	 for i := 0; i < len(personAll); i++ {
//	 	fmt.Println("Person ", personAll[i].Name, personAll[i].Phone)
//	 }
//
//}

type News struct {
	Title   string `bson:"Title"`
	Content string `bson:"Content"`
}

func hello(val string) string {
	var str = []byte(`[
                       {"Title": "News Service Complete", "Content": "Congratulations:Your News Service Complete"},
                       {"Title": "Total Ticket System Complete", "Content": "Just a total test"}
                    ]`)
	return string(str)
}

func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:12862")
}