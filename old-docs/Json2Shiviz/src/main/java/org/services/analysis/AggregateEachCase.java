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

package org.services.analysis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AggregateEachCase {
    private static String ROOT_DIR = "C:\\Users\\dingding\\Desktop\\tse debug exp\\F20\\shiviz\\4 scope failure elements\\shiviz_trace";
    private static String AGGREGATEFILENAME = "all.txt";

    public static void main(String[] args){
        List<File> dirList = getTestCaseDirList(ROOT_DIR);//Get case1, case2... directory
        for (int index1 = 0; index1 < dirList.size(); index1++) {

            String dirName = dirList.get(index1).getName();//case1, for example
            String destDir = String.format("%s\\%s", ROOT_DIR, dirName);
            String destFilePath = String.format("%s\\%s",destDir,AGGREGATEFILENAME);

            File destFile = new File(destFilePath);
            try {
                destFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(destFile);

                List<File> subDirList = getTestCaseDirList(destDir);//Get fail1,fail2... directory
                System.out.println(subDirList);
                for (int index2 = 0; index2 < subDirList.size(); index2++) {
                    List<File> fileLists = getListFiles(subDirList.get(index2));
                    //Aggreate all of the txt file
                    for (int x = 0; x < fileLists.size(); x++) {
                        File file = fileLists.get(x);
                        StringBuilder sb = new StringBuilder();
                        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                        BufferedReader br = new BufferedReader(reader);
                        String line = null;
                        while((line = br.readLine())!=null){
                            sb.append(line);
                            sb.append("\r\n");
                        }
                        reader.close();
                        fileOutputStream.write(sb.toString().getBytes());
                    }
                }
                fileOutputStream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //Get all the test case directory
    private static ArrayList<File> getTestCaseDirList(String path) {
        ArrayList<File> testDirs = new ArrayList<File>();
        File rootDir = new File(path);
        File[] dirList = rootDir.listFiles();
        for (int i = 0; i < dirList.length; i++) {
            File file = dirList[i];
            if (file.isDirectory())
                testDirs.add(file);
        }
        return testDirs;
    }

    //Get all the shiviz txt file
    private static ArrayList<File> getListFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        ArrayList<File> files = new ArrayList<File>();
        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                if (fileOne.getName().endsWith(".txt")) {
                    files.addAll(getListFiles(fileOne));
                }
            }
        }
        return files;
    }
}
